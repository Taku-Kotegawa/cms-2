package jp.co.stnet.cms.base.application.repository;

import jp.co.stnet.cms.base.domain.enums.Status;
import jp.co.stnet.cms.base.domain.model.Account;
import jp.co.stnet.cms.base.domain.model.mbg.TAccountExample;
import jp.co.stnet.cms.base.domain.model.mbg.TRole;
import jp.co.stnet.cms.base.domain.model.mbg.TRoleExample;
import jp.co.stnet.cms.base.infrastructure.mapper.mbg.TAccountMapper;
import jp.co.stnet.cms.base.infrastructure.mapper.mbg.TRoleMapper;
import org.apache.ibatis.session.RowBounds;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.rightPad;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class AccountRepositoryTest {

    @Autowired
    AccountRepository target;

    @Autowired
    TAccountMapper accountMapper;

    @Autowired
    TRoleMapper roleMapper;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    private void insertIntoDatabase(Account... accounts) {
        for (Account account : accounts) {
            accountMapper.insert(account);
            for (String role : account.getRoles()) {
                TRole tRole = new TRole();
                tRole.setUsername(account.getUsername());
                tRole.setRole(role);
                tRole.setDummy("X");
                roleMapper.insert(tRole);
            }
        }
    }

    private Account createEntity(String id) {
        Account account = new Account();
        account.setUsername(rightPad(id, 88, "0"));
        account.setPassword(rightPad("Password:" + id, 88, "0"));
        account.setFirstName(rightPad("FirstName:" + id, 128, "0"));
        account.setLastName(rightPad("LastName:" + id, 128, "0"));
        account.setEmail(rightPad("Email:" + id, 128, "0"));
        account.setUrl(rightPad("Url:" + id, 255, "0"));
        account.setProfile(rightPad("Profile:" + id, 100000, "0"));
        account.setDepartment(rightPad("Department:" + id, 255, "0"));
        account.setAllowedIp(rightPad("AllowedIp:" + id, 255, "0"));
        account.setApiKey(rightPad("ApiKey:" + id, 255, "0"));
        account.setStatus(Status.VALID.getCodeValue());

        account.setCreatedDate(LocalDateTime.of(2022, 12, 31, 1, 2, 3));
        account.setLastModifiedDate(LocalDateTime.of(2023, 1, 1, 12, 31, 59));

        account.getRoles().add(rightPad("role01", 10, "0"));
        account.getRoles().add(rightPad("role02", 10, "0"));
        account.getRoles().add(rightPad("role03", 10, "0"));
        account.getRoles().add(rightPad("role04", 10, "0"));
        account.getRoles().add(rightPad("role05", 10, "0"));
        account.getRoles().add(rightPad("role06", 10, "0"));
        account.getRoles().add(rightPad("role07", 10, "0"));
        account.getRoles().add(rightPad("role08", 10, "0"));
        account.getRoles().add(rightPad("role09", 10, "0"));
        account.getRoles().add(rightPad("role10", 10, "0"));

        return account;
    }

    private void deleteAll() {
        roleMapper.deleteByExample(new TRoleExample());
        accountMapper.deleteByExample(newExample());
    }

    private TAccountExample newExample() {
        return new TAccountExample();
    }

//    private void setNullWhoColumnList(List<Account> accounts) {
//        for (var account : accounts) {
//            setNullWhoColumn(account);
//        }
//    }
//
//    private void setNullWhoColumn(TAccount account) {
//        account.setCreatedBy(null);
//        account.setCreatedDate(null);
//        account.setLastModifiedBy(null);
//        account.setLastModifiedDate(null);
//        account.setVersion(null);
//    }

    // ------------------------------------------------------------------------------------------

    @Test
    private void prepare() {
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

    // ----------------------------------------------------------------------

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
        @DisplayName("[異常系]一意制約違反はDuplicateKeyExceptionをスロー")
        void test101() {
            // 準備
            deleteAll();
            insertIntoDatabase(
                    createEntity("1")
            );

            // 実行・検証
            assertThatThrownBy(() -> {
                target.register(createEntity("1"));
            }).isInstanceOf(DuplicateKeyException.class)
                    .hasMessageContaining("(username)=(1");
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
            expected.setFirstName(expected.getFirstName() + "a"); // 桁溢れ

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
        @DisplayName("[異常系]キーが同じ場合、一意制約違反()をスロー")
        void test101() {
            deleteAll();
            insertIntoDatabase(createEntity("1114"));

            // 実行
            var expected = List.of(
                    createEntity("1111"),
                    createEntity("1112"),
                    createEntity("1113"),
                    createEntity("1114"));

            assertThatThrownBy(() -> {
                target.registerAll(expected);
            }).isInstanceOf(DuplicateKeyException.class)
                    .hasMessageContaining("(username)=(1114");
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
            insertIntoDatabase(
                    createEntity("10")
            );

            // 実行
            var expected = target.findById(createEntity("10").getId()).orElseThrow();
            expected.setFirstName("Change");
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
            insertIntoDatabase(
                    createEntity("10")
            );

            var expected = createEntity("11");

            // 実行
            var actual = target.save(expected);

            // 検証
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("[正常系]ロール(明細データ)が更新できる・取得できる")
        void test003() {
            // 準備
            prepare();

            var expected = target.getOne(createEntity("10").getId());
            expected.setRoles(new ArrayList<>());
            expected.getRoles().add(rightPad("role01", 10, "0"));
            expected.getRoles().add(rightPad("role02", 10, "0"));
            expected.getRoles().add(rightPad("role03", 10, "0"));

            // 実行
            var actual = target.save(expected);

            // 検証
            assertThat(actual.getRoles()).size().isEqualTo(3);
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
            insertIntoDatabase(
                    createEntity("10")
            );

            var expected = createEntity("10");
            expected.setFirstName(expected.getFirstName() + "a"); // 桁溢れ

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
            insertIntoDatabase(
                    createEntity("10")
            );

            var expected = createEntity("10");
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
            insertIntoDatabase(
                    createEntity("10")
            );

            // 実行
            var expected = List.of(
                    target.findById(createEntity("10").getId()).orElseThrow(),
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
            insertIntoDatabase(
                    createEntity("10"), // NG
                    createEntity("11"), // OK
                    createEntity("23")  // NG
            );

            var expected = createEntity("11");

            // 実行
            var actual = target.findById(expected.getId());

            // 検証
            assertThat(actual.isPresent()).isTrue();


//            setNullWhoColumn(actual.get());
//            setNullWhoColumn(expected);
            assertThat(actual).isEqualTo(Optional.of(expected));
        }

        @Test
        @DisplayName("[異常系] 指定したキーのデータが未登録の場合、Optional.isPresent==false")
        void test101() {
            // 準備
            deleteAll();
            insertIntoDatabase(createEntity("1"));

            // 実行
            var actual = target.findById("NotExist");

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

            // 検証
            assertThatThrownBy(() -> {
                // 実行
                target.getOne(expected.getId());
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

            // 実行
            var actual = target.existsById(createEntity("10").getId());

            // 検証
            assertThat(actual).isTrue();
        }

        @Test
        @DisplayName("[正常系]存在しない場合、False")
        void test002() {
            // 準備
            prepare();

            // 実行
            var actual = target.existsById(createEntity("not exist").getId());

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

            // 実行
            var actual = target.findAllById(
                    List.of(
                            createEntity("01").getId(),
                            createEntity("02").getId()
                    )
            );

            // 検証
            assertThat(actual).size().isEqualTo(2);

            var expected = List.of(
                    createEntity("01"),
                    createEntity("02"));

//            setNullWhoColumnList(expected);
//            setNullWhoColumnList(actual);
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("[正常系]一致するデータがなければ、空のリストが返る")
        void test002() {
            // 準備
            prepare();

            // 実行
            var actual = target.findAllById(
                    List.of(
                            createEntity("a").getId(),
                            createEntity("b").getId()
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
            var actual = target.findAllById(new ArrayList<String>());

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
    class findAllByExample {

        @Test
        @DisplayName("[正常系]Exampleクラスで検索条件を指定して検索できる")
        void test001() {
            // 準備
            prepare();

            var example = new TAccountExample();
            example.or().andUsernameEqualTo(createEntity("01").getUsername());

            // 実行
            var actual = target.findAllByExample(example);

            // 検証
            assertThat(actual).size().isEqualTo(1);
        }

        @Test
        @DisplayName("[正常系]一致するデータがなければ、空のリストが返る")
        void test002() {
            // 準備
            prepare();

            var example = new TAccountExample();
            example.or().andUsernameEqualTo(createEntity("NOT EXIST").getUsername());

            // 実行
            var actual = target.findAllByExample(example);

            // 検証
            assertThat(actual).size().isEqualTo(0);
        }

        @Test
        @DisplayName("[正常系]空のExampleを渡すと全件取得できる")
        void test003() {
            // 準備
            prepare();

            // 実行
            var actual = target.findAllByExample(new TAccountExample());

            // 検証
            assertThat(actual).size().isEqualTo(10);
        }

        @Test
        @DisplayName("[正常系]Nullを渡すと全件取得できる")
        void test004() {
            // 準備
            prepare();

            // 実行
            var actual = target.findAllByExample(null);

            // 検証
            assertThat(actual).size().isEqualTo(10);
        }

    }

    @Nested
    class findAllByExampleWithRowBounds {

        @Test
        @DisplayName("[正常系]検索条件と取得件数を指定してデータを取得できる")
        void test001() {
            // 準備
            prepare();

            var example = new TAccountExample();
            example.or();
            example.setOrderByClause("username");
            var rowBounds = new RowBounds(2, 3);

            // 実行
            var actual = target.findAllByExampleWithRowBounds(example, rowBounds);

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
    class deleteById {

        @Test
        @DisplayName("[正常系]主キーで指定したデータを削除できる")
        void test001() {
            // 準備
            prepare();

            // 実行
            var deleteId = createEntity("01").getId();
            target.deleteById(deleteId);

            // 検証
            var actual = accountMapper.countByExample(null);
            assertThat(actual).isEqualTo(9);

            var actual2 = target.existsById(deleteId);
            assertThat(actual2).isFalse();
        }

        @Test
        @DisplayName("[正常系]主キーで指定したデータが存在しなくても、正常終了する")
        void test002() {
            // 準備
            prepare();

            // 実行
            var deleteId = createEntity("a").getId();
            target.deleteById(deleteId);

            // 検証
            var actual = accountMapper.countByExample(null);
            assertThat(actual).isEqualTo(10);

            var actual2 = target.existsById(deleteId);
            assertThat(actual2).isFalse();
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
            var actual = accountMapper.countByExample(null);
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

            // 実行
            var deleteEntities = List.of(
                    createEntity("01"),
                    createEntity("10"),
                    createEntity("99")  // 元々存在しないデータ
            );
            target.deleteAll(deleteEntities);

            // 検証
            var actual = accountMapper.countByExample(null);
            assertThat(actual).isEqualTo(8);

            assertThat(target.existsById(createEntity("01").getId())).isFalse();
            assertThat(target.existsById(createEntity("10").getId())).isFalse();
            assertThat(target.existsById(createEntity("99").getId())).isFalse();
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
            var example = new TAccountExample();
            example.or().andUsernameEqualTo(createEntity("10").getUsername());
            target.deleteByExample(example);

            // 検証
            var actual = accountMapper.countByExample(null);
            assertThat(actual).isEqualTo(9);
            assertThat(target.existsById(createEntity("10").getId())).isFalse();
        }
    }
}