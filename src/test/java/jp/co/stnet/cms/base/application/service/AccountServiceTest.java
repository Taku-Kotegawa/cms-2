package jp.co.stnet.cms.base.application.service;

import jp.co.stnet.cms.base.domain.enums.Status;
import jp.co.stnet.cms.base.domain.model.Account;
import jp.co.stnet.cms.base.domain.model.mbg.TAccount;
import jp.co.stnet.cms.base.domain.model.mbg.TAccountExample;
import jp.co.stnet.cms.base.domain.model.mbg.TRole;
import jp.co.stnet.cms.base.domain.model.mbg.TRoleExample;
import jp.co.stnet.cms.base.infrastructure.mapper.mbg.TAccountMapper;
import jp.co.stnet.cms.base.infrastructure.mapper.mbg.TRoleMapper;
import jp.co.stnet.cms.common.exception.DataIntegrityViolationBusinessException;
import jp.co.stnet.cms.common.exception.IllegalStateBusinessException;
import jp.co.stnet.cms.common.exception.OptimisticLockingFailureBusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.rightPad;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@Slf4j
@SpringBootTest
@Transactional
class AccountServiceTest {

    @Autowired
    AccountService target;

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

    private void setNullWhoColumnList(List<Account> accounts) {
        for (var account : accounts) {
            setNullWhoColumn(account);
        }
    }

    private void setNullWhoColumn(TAccount account) {
        account.setCreatedBy(null);
        account.setCreatedDate(null);
        account.setLastModifiedBy(null);
        account.setLastModifiedDate(null);
        account.setVersion(null);
    }

    // --------------------------------------------------------------------------------------------------------
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
    class findById {

        @Test
        void test001() {
            // 準備
            prepare();
            var expected = createEntity("01");

            // 実行
            var actual = target.findById(expected.getId());

            // 検証
            setNullWhoColumn(actual);
            assertThat(actual).isEqualTo(expected);
        }

    }

    @Nested
    class findPageByExampleWithRowBounds {

        @Test
        void test001() {
            // 準備
            prepare();

            // 実行
            var actual = target.findPageByExampleWithRowBounds(new TAccountExample(), new RowBounds());

            // 検証
            assertThat(actual.getContent()).size().isEqualTo(10);
        }

        @Test
        void test002() {
            // 準備
            prepare();

            var example = new TAccountExample();
            example.setOrderByClause("username");

            // 実行
            var actual = target.findPageByExampleWithRowBounds(example, new RowBounds(9, 2));

            System.out.println(actual.getContent().stream().map(Account::getUsername).toList());
            System.out.println(actual.getTotalElements());
            System.out.println(actual.getTotalPages());

            // 検証
            // assertThat(actual.getContent()).size().isEqualTo(1);
        }

    }

    @Nested
    class save {

        @Test
        @DisplayName("[正常系]存在しない場合、新規保存ができる。")
        void test_001() {
            // 準備
            prepare();

            // 実行
            var expected = createEntity("99"); // NOT EXIST
            var actual = target.save(expected);

            assertThat(expected).isEqualTo(actual);
        }

        @Test
        @DisplayName("[正常系]存在する場合、上書き保存できる。")
        void test_002() {
            // 準備
            prepare();

            // 実行
            var expectedId = createEntity("01").getId();
            var expected = target.findById(expectedId);
            expected.setFirstName("Changed");
            var actual = target.save(expected);

            // 検証
            expected.setVersion(expected.getVersion() + 1); // 検証用にversionを一致させる。
            assertThat(actual).isEqualTo(expected);

            expected.setFirstName("abc");
            assertThat(actual).isNotEqualTo(expected); // expectedとactualは別インスタンス
        }

        @Test
        @DisplayName("[異常系]楽観的排他制御でOptimisticLockingFailureBusinessExceptionをスローする")
        void test101() {
            // 準備
            prepare();

            // 実行
            var expected = createEntity("01");

            // 検証
            assertThatThrownBy(() -> {
                // 実行
                target.save(expected);
            }).isInstanceOf(OptimisticLockingFailureBusinessException.class);
        }

        @Test
        @DisplayName("[異常系]SQLエラーが発生した場合、DataIntegrityViolationBusinessExceptionをスローする")
        void test102() {
            // 準備
            prepare();

            // 実行
            var expected = createEntity("99");
            expected.setFirstName(expected.getFirstName() + "1");

            // 検証
            assertThatThrownBy(() -> {
                // 実行
                target.save(expected);
            }).isInstanceOf(DataIntegrityViolationBusinessException.class);
        }

    }

    @Nested
    class valid {

        @Test
        @DisplayName("ステータスがVALIDのデータは、INVALIDに変更できる。")
        void test001() {
            // 準備
            prepare();

            // 実行
            var expected = target.findById(createEntity("01").getId());
            expected = target.invalid(expected.getId());

            Account actual = new Account();
            if (expected.getStatus().equals(Status.INVALID.getCodeValue())) {
                actual = target.valid(expected.getId());
            }

            // 検証
            assertThat(actual.getStatus()).isEqualTo(Status.VALID.getCodeValue());
        }

        @Test
        @DisplayName("ステータスがVALIDのデータをVALIDに変更しようとするとIllegalStateBusinessExceptionをスロー")
        void test101() {
            // 準備
            prepare();

            var expected = target.findById(createEntity("01").getId());

            if (Status.VALID.getCodeValue().equals(expected.getStatus())) {
                // 検証
                assertThatThrownBy(() -> {
                    // 実行
                    var actual = target.valid(expected.getId());
                }).isInstanceOf(IllegalStateBusinessException.class);
            }
        }
    }

    @Nested
    class invalid {

        @Test
        @DisplayName("ステータスがVALIDのデータは、INVALIDに変更できる。")
        void test001() {
            // 準備
            prepare();

            // 実行
            var expected = target.findById(createEntity("01").getId());

            Account actual = new Account();
            if (expected.getStatus().equals(Status.VALID.getCodeValue())) {
                actual = target.invalid(expected.getId());
            }

            // 検証
            assertThat(actual.getStatus()).isEqualTo(Status.INVALID.getCodeValue());
        }

        @Test
        @DisplayName("ステータスがINVALIDのデータをINVALIDに変更しようとするとIllegalStateBusinessExceptionをスロー")
        void test101() {
            // 準備
            prepare();

            var expected = target.findById(createEntity("01").getId());
            target.invalid(expected.getId());

            // 検証
            assertThatThrownBy(() -> {
                // 実行
                var actual = target.invalid(expected.getId());
            }).isInstanceOf(IllegalStateBusinessException.class);

        }

    }

    @Nested
    class delete {
        @Test
        @DisplayName("[正常系]主キーで指定したデータを削除できる")
        void test001() {
            // 準備
            prepare();

            // 実行
            var deleteId = createEntity("01").getId();
            target.delete(deleteId);

            // 検証
            var actual = accountMapper.countByExample(null);
            assertThat(actual).isEqualTo(9);
        }
    }

    @Nested
    class equalsEntity {

        @Test
        @DisplayName("[正常] オブジェクトの比較(1) 同値の場合はTrueを返す")
        void test001() {
            // 実行
            var actual = target.equalsEntity(createEntity("1"), createEntity("1"));

            // 検証
            assertThat(actual).isTrue();
        }

        @Test
        @DisplayName("[正常] オブジェクトの比較(2) 同値の場合はTrueを返す")
        void test002() {

            var entity1 = createEntity("1");
            var entity2 = createEntity("1");
            entity1.setFirstName("abc");
            entity2.setFirstName("abc");

            // 実行
            var actual = target.equalsEntity(entity1, entity2);

            // 検証
            assertThat(actual).isTrue();
        }

        @Test
        @DisplayName("[正常] オブジェクトの比較 いづれかの項目が異なる場合はFalseを返す")
        void test003() {

            var entity1 = createEntity("1");
            var entity2 = createEntity("1");
            entity2.setStatus(Status.INVALID.getCodeValue()); // 一部の項目を変更

            // 実行
            var actual = target.equalsEntity(entity1, entity2);

            // 検証
            assertThat(actual).isFalse();
        }

        @Test
        @DisplayName("[正常] オブジェクトの比較 createdByは比較しない")
        void test004() {

            var entity1 = createEntity("1");
            var entity2 = createEntity("1");
            entity2.setCreatedBy("Changed");

            // 実行
            var actual = target.equalsEntity(entity1, entity2);

            // 検証
            assertThat(actual).isTrue();
        }

        @Test
        @DisplayName("[正常] オブジェクトの比較 createdDateは比較しない")
        void test005() {

            var entity1 = createEntity("1");
            var entity2 = createEntity("1");
            entity2.setCreatedDate(LocalDateTime.now());

            // 実行
            var actual = target.equalsEntity(entity1, entity2);

            // 検証
            assertThat(actual).isTrue();
        }

        @Test
        @DisplayName("[正常] オブジェクトの比較 lastModifiedByは比較しない")
        void test006() {

            var entity1 = createEntity("1");
            var entity2 = createEntity("1");
            entity2.setLastModifiedBy("Changed");

            // 実行
            var actual = target.equalsEntity(entity1, entity2);

            // 検証
            assertThat(actual).isTrue();
        }

        @Test
        @DisplayName("[正常] オブジェクトの比較 lastModifiedDateは比較しない")
        void test007() {

            var entity1 = createEntity("1");
            var entity2 = createEntity("1");
            entity2.setLastModifiedDate(LocalDateTime.now());

            // 実行
            var actual = target.equalsEntity(entity1, entity2);

            // 検証
            assertThat(actual).isTrue();
        }

        @Test
        @DisplayName("[正常] オブジェクトの比較 versionは比較しない")
        void test008() {

            var entity1 = createEntity("1");
            var entity2 = createEntity("1");
            entity2.setVersion(99L);

            // 実行
            var actual = target.equalsEntity(entity1, entity2);

            // 検証
            assertThat(actual).isTrue();
        }
    }

    @Nested
    class findAllById {

        @Test
        @DisplayName("")
        void test001() {
            // 準備
            prepare();

            var ids = List.of(
                    createEntity("01").getId(),
                    createEntity("02").getId(),
                    createEntity("99").getId()  // 存在しない番号
            );

            // 実行
            var actual = target.findAllById(ids);

            // 検証
            assertThat(actual).size().isEqualTo(2);
        }
    }

    @Nested
    class findPageByInput {
    }

    // --------------------------------------------------------------------------------------------------------

    @Nested
    class generateApiKey {

        @Test
        @DisplayName("[正常系]キーが発行できる")
        void test001() {
            // 準備

            // 実行
            var actual = target.generateApiKey();

            // 検証
            System.out.println(actual);
            assertThat(actual).isNotBlank();
        }
    }

    @Nested
    class deleteApiKey {

        @Test
        @DisplayName("[正常] usernameを指定して、API-KEYを削除できる")
        void test001() {
            // 準備
            prepare();

            var targetId = createEntity("01").getId();
            var update = TAccount.builder()
                    .username(targetId)
                    .apiKey("Change")
                    .build();
            accountMapper.updateByPrimaryKeySelective(update);

            // 実行
            var actual = target.deleteApiKey(targetId);

            // 検証
            assertThat(actual.getApiKey()).isNull();
        }

        // 存在しない場合

    }

    @Nested
    class saveApiKey {

        @Test
        @DisplayName("[正常] usernameを指定して、API-KEYを設定できる")
        void test001() {
            // 準備
            prepare();
            var targetId = createEntity("01").getId();

            // 実行
            var actual = target.saveApiKey(targetId);

            // 検証
            assertThat(actual.getApiKey()).isNotBlank();
            System.out.println(actual.getApiKey());
        }
    }

    @Nested
    class findByApiKey {

        @Test
        @DisplayName("[正常]API-KEYで検索できる。")
        void test001(){
            // 準備
            prepare();
            target.saveApiKey(createEntity("01").getId());
            target.saveApiKey(createEntity("02").getId());
            target.saveApiKey(createEntity("03").getId());
            target.saveApiKey(createEntity("04").getId());
            var expected = target.saveApiKey(createEntity("05").getId());
            target.saveApiKey(createEntity("06").getId());
            target.saveApiKey(createEntity("07").getId());
            target.saveApiKey(createEntity("08").getId());
            target.saveApiKey(createEntity("09").getId());
            target.saveApiKey(createEntity("10").getId());

            // 実行
            var actual = target.findByApiKey(expected.getApiKey());

            // 検証
            assertThat(actual).isEqualTo(expected);
        }
    }

}