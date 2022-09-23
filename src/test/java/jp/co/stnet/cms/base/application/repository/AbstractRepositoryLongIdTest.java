package jp.co.stnet.cms.base.application.repository;

import jp.co.stnet.cms.base.domain.model.KeyInterface;
import jp.co.stnet.cms.base.infrastructure.mapper.MapperInterface;
import org.apache.ibatis.session.RowBounds;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public abstract class AbstractRepositoryLongIdTest<T extends KeyInterface<I>, E, I> {

    Class<E> eClazz;

    abstract AbstractRepository<T, E, I> target();

    abstract MapperInterface<T, E, I> mapper();

    abstract T createEntity(String id);

    protected void insertIntoDatabase(T... entities) {
        for (T entity : entities) {
            mapper().insert(entity);
        }
    }

    abstract E newExample();

    protected void deleteAll() {
        mapper().deleteByExample(null);
    }

    // ------

    @Test
    void prepare() {
        deleteAll();
        insertIntoDatabase(
                createEntity("01"),
                createEntity("02"),
                createEntity("03"),
                createEntity("04"),
                createEntity("05"),
                createEntity("06"),
                createEntity("07"),
                createEntity("08"),
                createEntity("09"),
                createEntity("10")
        );
    }


    @Nested
    class register {

        @Test
        @DisplayName("[正常系]データが登録され、全項目に値がセットされる。登録済みのエンティティ")
        void test001() {
            // 準備
            deleteAll();

            // 実行
            var expected = createEntity("1111");
            var actual = target().register(expected);

            // 検証
            assertThat(actual).isEqualTo(expected); // 正しく登録されている
        }

        @Test
        @DisplayName("[異常系]Nullは受け取らない(NullPointerExceptionをスロー)")
        void test102() {
            // 準備

            // 実行・検証
            assertThatThrownBy(() -> {
                target().register(null);
            }).isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("[異常系]桁溢れはDataIntegrityViolationExceptionをスロー")
        void test103() {
            // 準備
            var expected = createEntity("2");
            setOverflowValue(expected);

            // 実行・検証
            assertThatThrownBy(() -> {
                target().register(expected);
            }).isInstanceOf(DataIntegrityViolationException.class);
        }
    }

    /**
     * 桁溢れを発生させる値をセット
     */
    abstract void setOverflowValue(T entity);

    @Nested
    class registerAll {

        @Test
        @DisplayName("[正常系]複数レコードを登録できる")
        void test001() {
            deleteAll();

            // 実行
            var expected = List.of(
                    createEntity("1111"),
                    createEntity("1112"),
                    createEntity("1113"),
                    createEntity("1114"));
            var actual = target().registerAll(expected);

            // 検証
            assertThat(actual).isEqualTo(expected); // 正しく登録されている
        }

        @Test
        @DisplayName("[異常系]Nullは受け取らない(NullPointerExceptionをスロー)")
        void test102() {
            // 準備

            // 実行・検証
            assertThatThrownBy(() -> {
                target().registerAll(null);
            }).isInstanceOf(NullPointerException.class);
        }
    }

    abstract void changeField(T entity);

    abstract void fixVersion(T entity);

    @Nested
    class save {

        @Test
        @DisplayName("[正常系]主キーを指定して更新できる")
        void test001() {
            // 準備
            deleteAll();
            var expected = target().register(createEntity("10"));
            changeField(expected);

            // 実行
            var actual = target().save(expected);

            // 検証
            fixVersion(expected);
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("[正常系]指定した主キーのデータが存在しなければ挿入")
        void test002() {
            // 準備
            deleteAll();

            var expected = createEntity("11");

            // 実行
            var actual = target().save(expected);

            // 検証
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("[異常系]Nullは受け取らない(NullPointerExceptionをスロー)")
        void test101() {
            // 実行・検証
            assertThatThrownBy(() -> {
                target().save(null);
            }).isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("[異常系]桁溢れはDataIntegrityViolationExceptionをスロー")
        void test102() {
            // 準備
            deleteAll();

            var expected = createEntity("10");
            setOverflowValue(expected);

            // 実行・検証
            assertThatThrownBy(() -> {
                target().save(expected);
            }).isInstanceOf(DataIntegrityViolationException.class);
        }

    }

    @Nested
    class saveAll {

        @Test
        @DisplayName("[正常系]複数レコードを登録・更新できる(混在可能)")
        void test001() {
            // 準備
            deleteAll();
            var saved = target().register(createEntity("10"));

            // 実行
            var expected = List.of(
                    saved,
                    createEntity("11"));

            var actual = target().saveAll(expected);

            // 検証
            fixVersionList(expected);
            assertThat(actual).isEqualTo(expected);
        }
    }

    abstract void fixVersionList(List<T> entities);

    @Nested
    class findById {

        @Test
        @DisplayName("[正常系]主キーを指定して抽出できること")
        void test001() throws InterruptedException {
            // 準備
            deleteAll();
            var expected = target().register(createEntity("10"));
            target().register(createEntity("11"));
            target().register(createEntity("12"));

            // 実行
            var actual = target().findById(expected.getId());

            // 検証
            assertThat(actual.isPresent()).isTrue();
            assertThat(actual).isEqualTo(Optional.of(expected));
        }

        @Test
        @DisplayName("[異常系] 指定したキーのデータが未登録の場合、Optional.isPresent==false")
        void test101() {
            // 準備
            deleteAll();

            // 実行
            var actual = target().findById(notExistId());

            // 検証
            assertThat(actual.isPresent()).isFalse();
        }
    }

    @Nested
    class getOne {

        @Test
        @DisplayName("[正常系]指定した主キーのデータがなければNotFoundExceptionがスロー")
        void test002() {
            // 準備
            deleteAll();

            // 検証
            assertThatThrownBy(() -> {
                // 実行
                target().getOne(notExistId());
            }).isInstanceOf(ResourceNotFoundException.class);
        }
    }

    abstract I notExistId();


    @Nested
    class existsById {

        @Test
        @DisplayName("[正常系]存在する場合、True")
        void test001() {
            // 準備
            prepare();
            var expected = target().register(createEntity("1"));

            // 実行
            var actual = target().existsById(expected.getId());

            // 検証
            assertThat(actual).isTrue();
        }

        @Test
        @DisplayName("[正常系]存在しない場合、False")
        void test002() {
            // 準備
            deleteAll();

            // 実行
            var actual = target().existsById(notExistId());

            // 検証
            assertThat(actual).isFalse();
        }
    }

    abstract List<I> notExistsIds();

    @Nested
    class findAllById {

        @Test
        @DisplayName("[正常系]キーを複数指定して検索できる")
        void test001() {
            // 準備
            deleteAll();
            var expected = List.of(
                    target().register(createEntity("11")).getId(),
                    target().register(createEntity("12")).getId()
            );

            // 実行
            var actual = target().findAllById(expected);

            // 検証
            assertThat(actual).size().isEqualTo(2);
        }

        @Test
        @DisplayName("[正常系]一致するデータがなければ、空のリストが返る")
        void test002() {
            // 準備
            prepare();

            // 実行
            var actual = target().findAllById(
                    notExistsIds()
            );

            // 検証
            assertThat(actual).size().isEqualTo(0);
        }


        @Test
        @DisplayName("[正常系]一致するデータがなければ、空のリストが返る")
        void test003() {
            // 準備
            prepare();

            // 実行
            var actual = target().findAllById(new ArrayList<I>());

            // 検証
            assertThat(actual).size().isEqualTo(0);
        }

        @Test
        @DisplayName("[異常系]Nullは受け取らない(NullPointerExceptionをスロー)")
        void test101() {
            // 準備
            // 検証
            assertThatThrownBy(() -> {
                // 実行
                target().findAllById(null);
            }).isInstanceOf(NullPointerException.class);
        }
    }

    abstract void setFindByCondition(E example);

    abstract void setNotFindByCondition(E example);

    @Nested
    class findAllByExample {

        @Test
        @DisplayName("[正常系]Exampleクラスで検索条件を指定して検索できる")
        void test001() {
            // 準備
            prepare();

            var example = newExample();
            setFindByCondition(example);

            // 実行
            var actual = target().findAllByExample(example);

            // 検証
            assertThat(actual).size().isEqualTo(1);
        }

        @Test
        @DisplayName("[正常系]一致するデータがなければ、空のリストが返る")
        void test002() {
            // 準備
            prepare();

            var example = newExample();
            setNotFindByCondition(example);

            // 実行
            var actual = target().findAllByExample(example);

            // 検証
            assertThat(actual).size().isEqualTo(0);
        }

        @Test
        @DisplayName("[正常系]空のExampleを渡すと全件取得できる")
        void test003() {
            // 準備
            prepare();

            // 実行
            var actual = target().findAllByExample(null);

            // 検証
            assertThat(actual).size().isEqualTo(10);
        }

        @Test
        @DisplayName("[正常系]Nullを渡すと全件取得できる")
        void test004() {
            // 準備
            prepare();

            // 実行
            var actual = target().findAllByExample(null);

            // 検証
            assertThat(actual).size().isEqualTo(10);
        }

    }

    abstract void setOrderByClause(E example);

    @Nested
    class findAllByExampleWithRowBounds {

        @Test
        @DisplayName("[正常系]検索条件と取得件数を指定してデータを取得できる")
        void test001() {
            // 準備
            prepare();

            var example = newExample();
            setOrderByClause(example);
            var rowBounds = new RowBounds(2, 3);

            // 実行
            var actual = target().findAllByExampleWithRowBounds(example, rowBounds);

            // 検証
            assertThat(actual).size().isEqualTo(3);
        }
    }

    @Nested
    class findAll {

        @Test
        @DisplayName("[正常系]テーブルに登録されている全件を取得できる")
        void test001() {
            // 準備
            prepare();

            // 実行
            var actual = target().findAll();

            // 検証
            assertThat(actual).size().isEqualTo(10);
        }

        @Test
        @DisplayName("[正常系]テーブルが空の場合、空のリストを取得できる")
        void test002() {
            // 準備
            deleteAll();

            // 実行
            var actual = target().findAll();

            // 検証
            assertThat(actual).size().isEqualTo(0);
        }
    }


    @Nested
    class deleteById {

        @Test
        @DisplayName("[正常系]主キーで指定したデータを削除できる")
        void test001() {
            // 準備
            deleteAll();
            var expected = target().register(createEntity("01"));

            // 実行
            target().deleteById(expected.getId());

            // 検証
            var actual = mapper().countByExample(null);
            assertThat(actual).isEqualTo(0);

            var actual2 = target().existsById(expected.getId());
            assertThat(actual2).isFalse();
        }

        @Test
        @DisplayName("[正常系]主キーで指定したデータが存在しなくても、正常終了する")
        void test002() {
            // 準備
            deleteAll();

            // 実行
            target().deleteById(notExistId());

            // 検証
            var actual = mapper().countByExample(null);
            assertThat(actual).isEqualTo(0);

            var actual2 = target().existsById(notExistId());
        }

        @Test
        @DisplayName("[異常系]Nullは受け取らない(NullPointerExceptionをスロー)")
        void test101() {
            // 検証
            assertThatThrownBy(() -> {
                // 実行
                target().deleteById(null);
            }).isInstanceOf(NullPointerException.class);
        }
    }

    @Nested
    class deleteAll {

        @Test
        @DisplayName("[正常系]すべてのデータを削除できる")
        void test001() {
            // 準備
            prepare();

            // 実行
            target().deleteAll();

            // 検証
            var actual = mapper().countByExample(null);
            assertThat(actual).isEqualTo(0);
        }
    }

    @Nested
    class deleteAll_ByEntities {

        @Test
        @DisplayName("[正常系]エンティティで指定した複数のデータを削除できる")
        void test001() {
            // 準備
            prepare();
            var entity1 = target().register(createEntity("11"));
            var entity2 = target().register(createEntity("12"));

            List<T> expected = List.of(
                    entity1,
                    entity2
            );

            // 実行
            target().deleteAll(expected);

            // 検証
            var actual = mapper().countByExample(null);
            assertThat(actual).isEqualTo(10);

            assertThat(target().existsById(entity1.getId())).isFalse();
            assertThat(target().existsById(entity2.getId())).isFalse();
        }
    }

    @Nested
    class deleteByExample {

        @Test
        @DisplayName("[正常系]Exampleクラスで指定したデータを削除できる")
        void test001() {
            // 準備
            prepare();

            // 実行
            var example = newExample();
            setFindByCondition(example);
            target().deleteByExample(example);

            // 検証
            var actual = mapper().countByExample(null);
            assertThat(actual).isEqualTo(9);
        }
    }

}
