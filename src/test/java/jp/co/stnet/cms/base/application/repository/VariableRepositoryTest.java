package jp.co.stnet.cms.base.application.repository;

import jp.co.stnet.cms.base.domain.model.mbg.Variable;
import jp.co.stnet.cms.base.domain.model.mbg.VariableExample;
import jp.co.stnet.cms.base.infrastructure.mapper.mbg.VariableMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.rightPad;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class VariableRepositoryTest {

    @Autowired
    VariableRepository target;

    @Autowired
    VariableMapper mapper;

    private static Variable createEntity(String id) {

        return Variable.builder()
                .version(1L)
                .status(rightPad("Status:" + id, 255, "0"))
                .type(rightPad("Type:" + id, 255, "0"))
                .code(rightPad("Code:" + id, 255, "0"))
                .value1(rightPad("value01:" + id, 255, "0"))
                .value2(rightPad("value02:" + id, 255, "0"))
                .value3(rightPad("value03:" + id, 255, "0"))
                .value4(rightPad("value04:" + id, 255, "0"))
                .value5(rightPad("value05:" + id, 255, "0"))
                .value6(rightPad("value06:" + id, 255, "0"))
                .value7(rightPad("value07:" + id, 255, "0"))
                .value8(rightPad("value08:" + id, 255, "0"))
                .value9(rightPad("value09:" + id, 255, "0"))
                .value10(rightPad("value10:" + id, 255, "0"))
                .date1(LocalDate.of(2021, 1, 1))
                .date2(LocalDate.of(2022, 2, 2))
                .date3(LocalDate.of(2023, 3, 3))
                .date4(LocalDate.of(2024, 4, 4))
                .date5(LocalDate.of(2025, 5, 5))
                .valint1(Integer.MAX_VALUE)
                .valint2(Integer.MAX_VALUE)
                .valint3(Integer.MAX_VALUE)
                .valint4(Integer.MAX_VALUE)
                .valint5(Integer.MIN_VALUE)
                .file1Uuid(rightPad("file1Uuid:" + id, 255, "0"))
                .textarea(rightPad("textarea:" + id, 1000, "0"))
                .remark(rightPad("file1Uuid:" + id, 255, "0"))
                .createdBy(rightPad("createdBy:" + id, 255, "0"))
                .createdDate(LocalDateTime.of(1999, 1, 1, 1, 11, 11))
                .lastModifiedBy(rightPad("lastModifiedBy:" + id, 255, "0"))
                .lastModifiedDate(LocalDateTime.of(2000, 2, 2, 2, 22, 22))
                .build();
    }

    private VariableExample newExample() {
        return new VariableExample();
    }

    private void insertIntoDatabase(Variable... variables) {
        for (Variable variable : variables) {
            mapper.insert(variable);
        }
    }

    private void deleteAll() {
        mapper.deleteByExample(newExample());
    }

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
            var actual = target.register(expected);

            // 検証
            assertThat(actual).isEqualTo(expected); // 正しく登録されている
        }

        @Test
        @DisplayName("[異常系]Nullは受け取らない(NullPointerExceptionをスロー)")
        void test102() {
            // 準備

            // 実行・検証
            assertThatThrownBy(() -> {
                target.register(null);
            }).isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("[異常系]桁溢れはDataIntegrityViolationExceptionをスロー")
        void test103() {
            // 準備
            var expected = createEntity("2");
            expected.setCode(expected.getCode() + "a"); // 桁溢れを準備

            // 実行・検証
            assertThatThrownBy(() -> {
                target.register(expected);
            }).isInstanceOf(DataIntegrityViolationException.class);
        }

    }

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
            var actual = target.registerAll(expected);

            // 検証
            assertThat(actual).isEqualTo(expected); // 正しく登録されている
        }

        @Test
        @DisplayName("[異常系]Nullは受け取らない(NullPointerExceptionをスロー)")
        void test102() {
            // 準備

            // 実行・検証
            assertThatThrownBy(() -> {
                target.registerAll(null);
            }).isInstanceOf(NullPointerException.class);
        }

    }

    @Nested
    class save {

        @Test
        @DisplayName("[正常系]主キーを指定して更新できる、バージョンが+1される")
        void test001() {
            // 準備
            deleteAll();
            var expected = createEntity("1");
            mapper.insert(expected);

            // 実行
            expected.setValue1("Change");
            var actual = target.save(expected);

            // 検証
            expected.setVersion(expected.getVersion() + 1L); // 比較用にバージョンを揃える
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("[正常系]指定した主キーのデータが存在しなければ挿入")
        void test002() {
            // 準備
            deleteAll();

            var expected = createEntity("1");

            // 実行
            var actual = target.save(expected);

            // 検証
            assertThat(actual).isEqualTo(expected);

            var count = mapper.countByExample(new VariableExample());
            assertThat(count).isEqualTo(1);
        }

        @Test
        @DisplayName("[異常系]Nullは受け取らない(NullPointerExceptionをスロー)")
        void test101() {
            // 実行・検証
            assertThatThrownBy(() -> {
                target.save(null);
            }).isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("[異常系]桁溢れはDataIntegrityViolationExceptionをスロー")
        void test102() {
            // 準備
            deleteAll();

            var expected = createEntity("10");
            expected.setCode(expected.getCode() + "a"); // 桁溢れ

            // 実行・検証
            assertThatThrownBy(() -> {
                target.save(expected);
            }).isInstanceOf(DataIntegrityViolationException.class);
        }

        @Test
        @DisplayName("[異常系]楽観的排他エラーをスローする")
        void test103() {
            // 準備
            deleteAll();
            var expected = createEntity("10");
            mapper.insert(expected);
            expected.setVersion(99L);

            // 実行・検証
            assertThatThrownBy(() -> {
                target.save(expected);
            }).isInstanceOf(OptimisticLockingFailureException.class);
        }

    }

    @Nested
    class saveAll {

        @Test
        @DisplayName("[正常系]複数レコードを登録・更新できる(混在可能)")
        void test001() {
            // 準備
            deleteAll();
            var saved = target.register(createEntity("10"));

            // 実行
            var expected = List.of(
                    saved,
                    createEntity("11"));

            var actual = target.saveAll(expected);

            // 検証
            expected.get(0).setVersion(expected.get(0).getVersion() + 1L); // 比較用にバージョンを揃える
            assertThat(actual).isEqualTo(expected);
        }

    }

    @Nested
    class findById {

        @Test
        @DisplayName("[正常系]主キーを指定して抽出できること")
        void test001() throws InterruptedException {
            // 準備
            deleteAll();
            var expected = target.register(createEntity("10"));
            target.register(createEntity("11"));
            target.register(createEntity("12"));

            // 実行
            var actual = target.findById(expected.getPrimaryKey());

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
            var actual = target.findById(999L); // ありえない値

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
            var expected = createEntity("11");
            expected.setId(999L);

            // 検証
            assertThatThrownBy(() -> {
                // 実行
                target.getOne(expected.getPrimaryKey());
            }).isInstanceOf(ResourceNotFoundException.class);
        }
    }

    @Nested
    class existsById {

        @Test
        @DisplayName("[正常系]存在する場合、True")
        void test001() {
            // 準備
            prepare();
            var expected = target.register(createEntity("1"));

            // 実行
            var actual = target.existsById(expected.getPrimaryKey());

            // 検証
            assertThat(actual).isTrue();
        }

        @Test
        @DisplayName("[正常系]存在しない場合、False")
        void test002() {
            // 準備
            deleteAll();

            // 実行
            var actual = target.existsById(999L); // ありえない値

            // 検証
            assertThat(actual).isFalse();
        }
    }

    @Nested
    class findAllById {


        @Test
        @DisplayName("[正常系]キーを複数指定して検索できる")
        void test001() {
            // 準備
            prepare();

            var saved = createEntity("01");
            var list = target.findAllByTypeAndCode(saved.getType(), saved.getCode());


            // 実行
            var actual = target.findAllById(
                    List.of(
                            list.get(0).getPrimaryKey(),
                            list.get(0).getPrimaryKey() + 1L
                    )
            );

            // 検証
            assertThat(actual).size().isEqualTo(2);

        }

        @Test
        @DisplayName("[正常系]一致するデータがなければ、空のリストが返る")
        void test002() {
            // 準備
            prepare();

            // 実行
            var actual = target.findAllById(
                    List.of(
                            98L,
                            99L
                    )
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
            var actual = target.findAllById(new ArrayList<Long>());

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
                target.findAllById(null);
            }).isInstanceOf(NullPointerException.class);
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
            var actual = target.findAll();

            // 検証
            assertThat(actual).size().isEqualTo(10);
        }

        @Test
        @DisplayName("[正常系]テーブルが空の場合、空のリストを取得できる")
        void test002() {
            // 準備
            deleteAll();

            // 実行
            var actual = target.findAll();

            // 検証
            assertThat(actual).size().isEqualTo(0);
        }

    }

    @Nested
    class delete {

    }

    @Nested
    class deleteById {

        @Test
        @DisplayName("[正常系]主キーで指定したデータを削除できる")
        void test001() {
            // 準備
            deleteAll();
            var entity1 = target.register(createEntity("01"));
            var entity2 = target.register(createEntity("02"));
            var entity3 = target.register(createEntity("03"));
            var entity4 = target.register(createEntity("04"));
            var entity5 = target.register(createEntity("05"));
            var entity6 = target.register(createEntity("06"));
            var entity7 = target.register(createEntity("07"));
            var entity8 = target.register(createEntity("08"));
            var entity9 = target.register(createEntity("09"));

            // 実行
            target.deleteById(entity1.getPrimaryKey());

            // 検証
            var actual = mapper.countByExample(newExample());
            assertThat(actual).isEqualTo(8);

            var actual2 = target.existsById(entity1.getPrimaryKey());
            assertThat(actual2).isFalse();
        }

        @Test
        @DisplayName("[正常系]主キーで指定したデータが存在しなくても、正常終了する")
        void test002() {
            // 準備
            prepare();

            // 実行
            target.deleteById(999l);

            // 検証
            var actual = mapper.countByExample(newExample());
            assertThat(actual).isEqualTo(10);
        }

        @Test
        @DisplayName("[異常系]Nullは受け取らない(NullPointerExceptionをスロー)")
        void test101() {
            // 検証
            assertThatThrownBy(() -> {
                // 実行
                target.deleteById(null);
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
            target.deleteAll();

            // 検証
            var actual = mapper.countByExample(newExample());
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
            var entities = target.findAllByType(createEntity("01").getType());

            // 実行
            target.deleteAll(entities);

            // 検証
            var actual = mapper.countByExample(newExample());
            assertThat(actual).isEqualTo(9);

            for (var entity : entities) {
                assertThat(target.existsById(entity.getPrimaryKey()));
            }
        }
    }

    @Nested
    class deleteByExample {

    }

    @Nested
    class deleteAllById {
    }

    @Nested
    class findAllByExample {
    }

    @Nested
    class findAllByExampleWithRowBounds {
    }

    @Nested
    class findAllByTypeAndCode {

        @Test
        @DisplayName("[正常系]指定したデータを検索できる")
        void test002() {
            // 準備
            prepare();
            var expected = createEntity("01");

            // 実行
            var actual = target.findAllByTypeAndCode(expected.getType(), expected.getCode());

            // 検証
            assertThat(actual).size().isEqualTo(1);
            assertThat(actual.get(0).getType()).isEqualTo(expected.getType());
            assertThat(actual.get(0).getCode()).isEqualTo(expected.getCode());
        }

    }

    @Nested
    class findAllByTypeAndValue1 {

        @Test
        @DisplayName("[正常系]指定したデータを検索できる")
        void test001() {
            // 準備
            prepare();
            var expected = createEntity("01");

            // 実行
            var actual = target.findAllByTypeAndValue1(expected.getType(), expected.getValue1());

            // 検証
            assertThat(actual).size().isEqualTo(1);
            assertThat(actual.get(0).getType()).isEqualTo(expected.getType());
            assertThat(actual.get(0).getValue1()).isEqualTo(expected.getValue1());
        }
    }

    @Nested
    class findAllByTypeAndValue2 {
        @Test
        @DisplayName("[正常系]指定したデータを検索できる")
        void test001() {
            // 準備
            prepare();
            var expected = createEntity("01");

            // 実行
            var actual = target.findAllByTypeAndValue2(expected.getType(), expected.getValue2());

            // 検証
            assertThat(actual).size().isEqualTo(1);
            assertThat(actual.get(0).getType()).isEqualTo(expected.getType());
            assertThat(actual.get(0).getValue2()).isEqualTo(expected.getValue2());
        }
    }

    @Nested
    class findAllByTypeAndValue3 {
        @Test
        @DisplayName("[正常系]指定したデータを検索できる")
        void test001() {
            // 準備
            prepare();
            var expected = createEntity("01");

            // 実行
            var actual = target.findAllByTypeAndValue3(expected.getType(), expected.getValue3());

            // 検証
            assertThat(actual).size().isEqualTo(1);
            assertThat(actual.get(0).getType()).isEqualTo(expected.getType());
            assertThat(actual.get(0).getValue3()).isEqualTo(expected.getValue3());
        }
    }

    @Nested
    class findAllByTypeAndValue4 {
        @Test
        @DisplayName("[正常系]指定したデータを検索できる")
        void test001() {
            // 準備
            prepare();
            var expected = createEntity("01");

            // 実行
            var actual = target.findAllByTypeAndValue4(expected.getType(), expected.getValue4());

            // 検証
            assertThat(actual).size().isEqualTo(1);
            assertThat(actual.get(0).getType()).isEqualTo(expected.getType());
            assertThat(actual.get(0).getValue4()).isEqualTo(expected.getValue4());
        }
    }

    @Nested
    class findAllByTypeAndValue5 {

        @Test
        @DisplayName("[正常系]指定したデータを検索できる")
        void test001() {
            // 準備
            prepare();
            var expected = createEntity("01");

            // 実行
            var actual = target.findAllByTypeAndValue5(expected.getType(), expected.getValue5());

            // 検証
            assertThat(actual).size().isEqualTo(1);
            assertThat(actual.get(0).getType()).isEqualTo(expected.getType());
            assertThat(actual.get(0).getValue5()).isEqualTo(expected.getValue5());
        }
    }

    @Nested
    class findAllByTypeAndValue6 {

        @Test
        @DisplayName("[正常系]指定したデータを検索できる")
        void test001() {
            // 準備
            prepare();
            var expected = createEntity("01");

            // 実行
            var actual = target.findAllByTypeAndValue6(expected.getType(), expected.getValue6());

            // 検証
            assertThat(actual).size().isEqualTo(1);
            assertThat(actual.get(0).getType()).isEqualTo(expected.getType());
            assertThat(actual.get(0).getValue6()).isEqualTo(expected.getValue6());
        }

    }

    @Nested
    class findAllByTypeAndValue7 {

        @Test
        @DisplayName("[正常系]指定したデータを検索できる")
        void test001() {
            // 準備
            prepare();
            var expected = createEntity("01");

            // 実行
            var actual = target.findAllByTypeAndValue7(expected.getType(), expected.getValue7());

            // 検証
            assertThat(actual).size().isEqualTo(1);
            assertThat(actual.get(0).getType()).isEqualTo(expected.getType());
            assertThat(actual.get(0).getValue7()).isEqualTo(expected.getValue7());
        }

    }

    @Nested
    class findAllByTypeAndValue8 {

        @Test
        @DisplayName("[正常系]指定したデータを検索できる")
        void test001() {
            // 準備
            prepare();
            var expected = createEntity("01");

            // 実行
            var actual = target.findAllByTypeAndValue8(expected.getType(), expected.getValue8());

            // 検証
            assertThat(actual).size().isEqualTo(1);
            assertThat(actual.get(0).getType()).isEqualTo(expected.getType());
            assertThat(actual.get(0).getValue8()).isEqualTo(expected.getValue8());
        }
    }

    @Nested
    class findAllByTypeAndValue9 {

        @Test
        @DisplayName("[正常系]指定したデータを検索できる")
        void test001() {
            // 準備
            prepare();
            var expected = createEntity("01");

            // 実行
            var actual = target.findAllByTypeAndValue9(expected.getType(), expected.getValue9());

            // 検証
            assertThat(actual).size().isEqualTo(1);
            assertThat(actual.get(0).getType()).isEqualTo(expected.getType());
            assertThat(actual.get(0).getValue9()).isEqualTo(expected.getValue9());
        }
    }

    @Nested
    class findAllByTypeAndValue10 {

        @Test
        @DisplayName("[正常系]指定したデータを検索できる")
        void test001() {
            // 準備
            prepare();
            var expected = createEntity("01");

            // 実行
            var actual = target.findAllByTypeAndValue10(expected.getType(), expected.getValue10());

            // 検証
            assertThat(actual).size().isEqualTo(1);
            assertThat(actual.get(0).getType()).isEqualTo(expected.getType());
            assertThat(actual.get(0).getValue10()).isEqualTo(expected.getValue10());
        }
    }

    @Nested
    class findAllByType {

        @Test
        @DisplayName("[正常系]指定したデータを検索できる")
        void test001() {
            // 準備
            prepare();
            var expected = createEntity("01");

            // 実行
            var actual = target.findAllByType(expected.getType());

            // 検証
            assertThat(actual).size().isEqualTo(1);
            assertThat(actual.get(0).getType()).isEqualTo(expected.getType());
        }
    }

    @Nested
    class findPageByInput {
    }
}