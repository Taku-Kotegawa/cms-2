package jp.co.stnet.cms.base.application.repository;

import jp.co.stnet.cms.base.domain.enums.Status;
import jp.co.stnet.cms.base.domain.model.Account;
import jp.co.stnet.cms.base.domain.model.mbg.TAccountExample;
import jp.co.stnet.cms.base.domain.model.mbg.TRoleExample;
import jp.co.stnet.cms.base.infrastructure.mapper.mbg.TAccountMapper;
import jp.co.stnet.cms.base.infrastructure.mapper.mbg.TRoleMapper;
import org.apache.ibatis.session.RowBounds;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.rightPad;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class AccountTestRepositoryTest {

    @Autowired
    AccountTestRepository target;

    @Autowired
    TRoleMapper roleMapper;

    @Autowired
    TAccountMapper accountMapper;

    /**
     * accountテーブルにデータを挿入する。 (Repository.insert使用)
     *
     * @param accounts Account(カンマ区切りで複数指定可)
     */
    private void insertIntoDatabase(Account... accounts) {
        for (Account account : accounts) {
            target.insert(account);
        }
    }

    /**
     * テストエンティティの作成(各フィールドに最大桁数のダミーデータをセット)
     *
     * @param id テストデータを一意に特定する番号
     * @return Accountエンティティ
     */
    private Account createEntity(String id) {
        var account = new Account();
        account.setUsername(rightPad(id, 88, "0"));
        account.setPassword(rightPad("Password:" + id, 88, "0"));
        account.setFirstName(rightPad("FirstName:" + id, 128, "0"));
        account.setLastName(rightPad("LastName:" + id, 128, "0"));
        account.setEmail(rightPad("Email:" + id, 128, "0"));
        account.setUrl(rightPad("Url:" + id, 255, "0"));
        account.setProfile(rightPad("Profile:" + id, 100000, "0"));
        account.setStatus(Status.VALID.getCodeValue());

        account.setRoles(new ArrayList<>());
        account.getRoles().add(rightPad("ROLE_01", 10, "0"));
        account.getRoles().add(rightPad("ROLE_02", 10, "0"));
        account.getRoles().add(rightPad("ROLE_03", 10, "0"));
        account.getRoles().add(rightPad("ROLE_04", 10, "0"));
        account.getRoles().add(rightPad("ROLE_05", 10, "0"));
        account.getRoles().add(rightPad("ROLE_06", 10, "0"));
        account.getRoles().add(rightPad("ROLE_07", 10, "0"));
        account.getRoles().add(rightPad("ROLE_08", 10, "0"));
        account.getRoles().add(rightPad("ROLE_09", 10, "0"));
        account.getRoles().add(rightPad("ROLE_10", 10, "0"));

        return account;
    }

    /**
     * Accountテーブル全件削除(依存するテーブルも含む)
     */
    private void deleteAll() {
        roleMapper.deleteByExample(new TRoleExample());
        accountMapper.deleteByExample(example());
    }

    private TAccountExample example() {
        return new TAccountExample();
    }

    private void setNullWhoColumnList(List<Account> accountAndRolesList) {
        for (var accountRole : accountAndRolesList) {
            setNullWhoColumn(accountRole);
        }
    }

    private void setNullWhoColumn(Account accountAndRoles) {
        accountAndRoles.setCreatedBy(null);
        accountAndRoles.setCreatedDate(null);
        accountAndRoles.setLastModifiedBy(null);
        accountAndRoles.setLastModifiedDate(null);
        accountAndRoles.setVersion(null);
    }


    // ----------------------------------------------------------------------

    @Nested
    class builder {
        @Test
        void test01(){
//            Account.builder()
//                    .username("a")
//                    .password("b")
//                    .allowedIp("")
//                    .build();
//
//            Account.builder()
//                    .username("a")
//                    .build();
        }
    }

    @Nested
    class countByExample {

        @Test
        @DisplayName("[正常系]テーブルに登録された件数を返す(0件)")
        void test001() {
            // 準備
            deleteAll();

            // 実行
            long actualCount = target.countByExample(example());

            // 検証
            assertThat(actualCount).isEqualTo(0L);
        }

        @Test
        @DisplayName("[正常系]テーブルに登録された件数を返す(1件)")
        void test002() {
            // 準備
            deleteAll();
            insertIntoDatabase(
                    createEntity("1") // OK
            );

            // 実行
            long actualCount = target.countByExample(example());

            // 検証
            assertThat(actualCount).isEqualTo(1L);
        }

        @Test
        @DisplayName("[正常系]テーブルに登録された件数を返す(2件)")
        void test003() {
            // 準備
            deleteAll();
            insertIntoDatabase(
                    createEntity("1"), // OK
                    createEntity("2")  // OK
            );

            // 実行
            long actualCount = target.countByExample(example());

            // 検証
            assertThat(actualCount).isEqualTo(2L);
        }

        @Test
        @DisplayName("[正常系]テーブルに登録されたデータから検索条件に合致する件数を返す")
        void test004() {
            // 準備
            deleteAll();
            insertIntoDatabase(
                    createEntity("1"), // NG
                    createEntity("2")  // OK
            );

            // 実行
            var example = example();
            example.or().andPasswordLike("Password:2%");
            long actualCount = target.countByExample(example);

            // 検証
            assertThat(actualCount).isEqualTo(1L);
        }

    }

    @Nested
    class deleteByExample {

        @Test
        @DisplayName("[正常系]条件に合致したデータを削除する、戻り値は削除件数。(1件)")
        void test001() {
            // 準備
            deleteAll();
            insertIntoDatabase(
                    createEntity("1"), // NOT
                    createEntity("2"), // DELETE
                    createEntity("3")  // NOT
            );

            // 実行
            var example = example();
            example.or().andPasswordLike("Password:2%");
            int actualDeleteCount = target.deleteByExample(example);

            // 検証
            assertThat(actualDeleteCount).isEqualTo(1L); // 削除件数は1件

            long actualCount = target.countByExample(example);
            assertThat(actualCount).isEqualTo(0L); // テーブルに残っていない

            long actualExistCount = target.countByExample(example());
            assertThat(actualExistCount).isEqualTo(2L); // 2件残る

        }

        @Test
        @DisplayName("[正常系]条件に合致したデータを削除する、戻り値は削除件数。(2件)")
        void test002() {
            // 準備
            deleteAll();
            insertIntoDatabase(
                    createEntity("10"), // OK
                    createEntity("11"), // OK
                    createEntity("20")  // NG
            );

            // 実行
            var example = example();
            example.or().andPasswordLike("Password:1%");
            int actualDeleteCount = target.deleteByExample(example);

            // 検証
            assertThat(actualDeleteCount).isEqualTo(2L); // 削除件数は2件

            long actualCount = target.countByExample(example);
            assertThat(actualCount).isEqualTo(0L); // テーブルに残っていない

            long actualExistCount = target.countByExample(example());
            assertThat(actualExistCount).isEqualTo(1L); // 1件残る
        }

    }

    @Nested
    class deleteByPrimaryKey {

        @Test
        @DisplayName("[正常系]指定した主キーでデータを削除する、戻り値は削除件数。")
        void test001() {
            // 準備
            deleteAll();
            insertIntoDatabase(
                    createEntity("10"), // NOT
                    createEntity("11"), // DELETE
                    createEntity("20")  // NOT
            );

            // 実行
            int actualDeleteCount = target.deleteByPrimaryKey(rightPad("11", 88, "0"));

            // 検証
            assertThat(actualDeleteCount).isEqualTo(1L); // 削除件数は1件

            var example = example();
            example.or().andUsernameEqualTo(rightPad("11", 88, "0"));
            long actualExistCount = target.countByExample(example);
            assertThat(actualExistCount).isEqualTo(0L); // 残っていない

            long actualExistCount2 = target.countByExample(example());
            assertThat(actualExistCount2).isEqualTo(2L); // 2件残る
        }

    }

    @Nested
    class insert {

        @Test
        @DisplayName("[正常系]データが登録され、全項目に値がセットされる。戻り値は登録件数。")
        void test001() {
            // 準備
            deleteAll();

            // 実行
            var expected = createEntity("1");
            int actualCount = target.insert(expected);

            // 検証
            assertThat(actualCount).isEqualTo(1L); // 登録件数は1件

            var actual = target.selectByPrimaryKey(expected.getUsername());
            assertThat(actual).isEqualTo(expected); // 正しく登録されている
        }

        @Test
        @DisplayName("[異常系]一意制約違反でSQL例外がスローされる。")
        void test101() {
            // 準備
            deleteAll();
            insertIntoDatabase(
                    createEntity("1")
            );

            // 実行・検証
            assertThatThrownBy(() -> {
                target.insert(createEntity("1"));
            }).isInstanceOf(DuplicateKeyException.class)
                    .hasMessageContaining("(username)=(1");
        }

    }

    @Nested
    class insertSelective {
        @Test
        @DisplayName("[正常系]データが登録され、全項目に値がセットされる。戻り値は登録件数。(DBの初期値の検証なし)")
        void test001() {
            // 準備
            deleteAll();

            // 実行
            var expected = createEntity("1");
            int count = target.insertSelective(expected);

            // 検証
            assertThat(count).isEqualTo(1L);

            var actual = target.selectByPrimaryKey(expected.getUsername());
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("[異常系]一意制約違反でSQL例外がスローされる。")
        void test101() {
            // 準備
            deleteAll();
            insertIntoDatabase(
                    createEntity("1")
            );

            // 実行・検証
            assertThatThrownBy(() -> {
                target.insert(createEntity("1"));
            }).isInstanceOf(DuplicateKeyException.class)
                    .hasMessageContaining("(username)=(1");
        }
    }

    @Nested
    class selectByExampleWithRowbounds {

        @Test
        @DisplayName("[正常系]件数と検索条件を指定して抽出できること")
        void test001() {
            // 準備
            deleteAll();
            insertIntoDatabase(
                    createEntity("10"), // NG
                    createEntity("11"), // NG
                    createEntity("12"), // OK
                    createEntity("13"), // OK
                    createEntity("14"), // OK
                    createEntity("15"), // NG
                    createEntity("23")  // NG
            );

            // 実行
            RowBounds rowBounds = new RowBounds(2, 3);
            var example = example();
            example.or().andPasswordBetween("Password:10", "Password:20");
            example.setOrderByClause("username");
            var actuals = target.selectByExampleWithRowbounds(example, rowBounds);

            // 検証
            setNullWhoColumnList(actuals);
            assertThat(actuals)
                    .hasSize(3)
                    .containsOnly(
                            createEntity("12"),
                            createEntity("13"),
                            createEntity("14")
                    );
        }

    }

    @Nested
    class selectByExample {

        @Test
        @DisplayName("[正常系]検索条件を指定して抽出できること")
        void test001() {
            // 準備
            deleteAll();
            insertIntoDatabase(
                    createEntity("10"), // NG
                    createEntity("11"), // OK
                    createEntity("12"), // OK
                    createEntity("23")  // NG
            );

            // 実行
            var example = example();
            example.or().andPasswordBetween("Password:11", "Password:20");
            example.setOrderByClause("username");
            var actuals = target.selectByExample(example);

            // 検証
            setNullWhoColumnList(actuals);
            assertThat(actuals)
                    .hasSize(2)
                    .containsOnly(
                            createEntity("11"),
                            createEntity("12")
                    );
        }

    }


    @Nested
    class selectByPrimaryKey {

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

            // 実行
            var actual = target.selectByPrimaryKey(rightPad("11", 88, "0"));

            // 検証
            setNullWhoColumn(actual);
            var expected = createEntity("11");
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("[異常系] 指定したキーのデータが未登録の場合はnull")
        void test101() {
            // 準備
            deleteAll();
            insertIntoDatabase(createEntity("1"));

            // 実行
            var actual = target.selectByPrimaryKey("NotExist");

            // 検証
            assertThat(actual).isNull();
        }

    }

    @Nested
    class updateByExampleSelective {

        @Test
        @DisplayName("[正常系]条件を指定して更新できること、戻値は更新件数")
        void test001() {
            // 準備
            deleteAll();
            insertIntoDatabase(
                    createEntity("10"),
                    createEntity("11"),
                    createEntity("12"),
                    createEntity("20")
            );

            // 実行
            var expected = createEntity("11");
            expected.setPassword("change");

            var example = example();
            example.or().andUsernameEqualTo(rightPad("11", 88, "0"));
            int actualCount = target.updateByExampleSelective(expected, example);

            // 検証
            assertThat(actualCount).isEqualTo(1);

            var actual = target.selectByPrimaryKey(expected.getUsername());
            setNullWhoColumn(actual);
            setNullWhoColumn(expected);
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("[正常系]指定した条件のデータがなければ0が返る。")
        void test002() {
            // 準備
            deleteAll();
            insertIntoDatabase(
                    createEntity("10"),
                    createEntity("11"),
                    createEntity("12"),
                    createEntity("20")
            );

            // 実行
            var expected = createEntity("11");
            var example = example();
            example.or().andEmailEqualTo("NOT EXIST");
            int actualCount = target.updateByExampleSelective(expected, example);

            // 検証
            assertThat(actualCount).isEqualTo(0);
        }

//        @Test
//        @DisplayName("[正常系]特定の条件で選択可能な複数のデータの特定の項目を一括更新、戻値は更新件数")
//        void test003() {
//            // 準備
//            deleteAll();
//            insertIntoDatabase(
//                    createEntity("10"),
//                    createEntity("11"),
//                    createEntity("12"),
//                    createEntity("20")
//            );
//
//            // 実行
//            var expected = new Account();
//            expected.setPassword("Change");
//            var example = example();
//            example.or().andUsernameNotEqualTo(rightPad("20", 88, "0"));
//            int actualCount = target.updateByExampleSelective(expected, example);
//
//            // 検証
//            assertThat(actualCount).isEqualTo(3);
//
//            var actuals = target.selectByExample(example);
//            setNullWhoColumnList(actuals);
//            assertThat(actuals)
//                    .extracting(Account::getPassword)
//                    .containsOnly("Change");
//        }
    }

    @Nested
    class updateByExample {

        @Test
        @DisplayName("[正常系]条件を指定して更新できること、戻値は更新件数")
        void test001() {
            // 準備
            deleteAll();
            insertIntoDatabase(
                    createEntity("10"),
                    createEntity("11"),
                    createEntity("12"),
                    createEntity("20")
            );

            // 実行
            var expected = createEntity("11");
            expected.setPassword("change");

            var example = example();
            example.or().andUsernameEqualTo(rightPad("11", 88, "0"));
            int actualCount = target.updateByExample(expected, example);

            // 検証
            assertThat(actualCount).isEqualTo(1);

            var actual = target.selectByPrimaryKey(expected.getUsername());
            setNullWhoColumn(actual);
            setNullWhoColumn(expected);
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("[正常系]指定した条件のデータがなければ0が返る。")
        void test002() {
            // 準備
            deleteAll();
            insertIntoDatabase(
                    createEntity("10"),
                    createEntity("11"),
                    createEntity("12"),
                    createEntity("20")
            );

            // 実行
            var expected = createEntity("11");
            var example = example();
            example.or().andEmailEqualTo("NOT EXIST");
            int actualCount = target.updateByExample(expected, example);

            // 検証
            assertThat(actualCount).isEqualTo(0);
        }

    }

    @Nested
    class updateByPrimaryKeySelective {

        @Test
        @DisplayName("[正常系]主キーを指定して更新できること、戻値は更新件数")
        void test001() {
            // 準備
            deleteAll();
            insertIntoDatabase(
                    createEntity("10")
            );

            // 実行
            var expected = createEntity("11");
            expected.setUsername(rightPad("10", 88, "0"));
            int actualCount = target.updateByPrimaryKeySelective(expected);

            // 検証
            assertThat(actualCount).isEqualTo(1);

            var actual = target.selectByPrimaryKey(expected.getUsername());
            setNullWhoColumn(actual);
            setNullWhoColumn(expected);
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("[正常系]指定した主キーのデータがなければ0が返る。")
        void test002() {
            // 準備
            deleteAll();
            insertIntoDatabase(
                    createEntity("10")
            );

            // 実行
            var expected = createEntity("11");
            int actualCount = target.updateByPrimaryKeySelective(expected);

            // 検証
            assertThat(actualCount).isEqualTo(0);
        }

        @Test
        @DisplayName("[正常系]特定の項目のみ更新する。")
        void test003() {
            // 準備
            deleteAll();
            insertIntoDatabase(
                    createEntity("10")
            );

            // 実行
            var expected = new Account();
            expected.setUsername(rightPad("10", 88, "0"));
            expected.setFirstName("Change");
            target.updateByPrimaryKeySelective(expected);

            // 検証
            Account actual = target.selectByPrimaryKey(rightPad("10", 88, "0"));
            assertThat(actual.getFirstName()).isEqualTo("Change");

            // FirstName以外を比較
            assertThat(actual);

        }
    }

    @Nested
    class updateByPrimaryKey {

        @Test
        @DisplayName("[正常系]主キーを指定して更新できること、戻値は更新件数")
        void test001() {
            // 準備
            deleteAll();
            insertIntoDatabase(
                    createEntity("10")
            );

            // 実行
            var expected = createEntity("11");
            expected.setUsername(rightPad("10", 88, "0"));
            int count = target.updateByPrimaryKey(expected);

            // 検証
            assertThat(count).isEqualTo(1);
            var actual = target.selectByPrimaryKey(expected.getUsername());
            setNullWhoColumn(expected);
            setNullWhoColumn(actual);
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("[正常系]指定した主キーのデータがなければ0が返る。")
        void test002() {
            // 準備
            deleteAll();
            insertIntoDatabase(
                    createEntity("10")
            );

            // 実行
            var expected = createEntity("11");
            int actualCount = target.updateByPrimaryKey(expected);

            // 検証
            assertThat(actualCount).isEqualTo(0);
        }
    }

    @Nested
    class updateByPrimaryKeyAndVersionSelective {

        @Test
        @DisplayName("[正常系]主キーを指定して更新できること、戻値は更新件数")
        void test001() {
            // 準備
            deleteAll();
            insertIntoDatabase(
                    createEntity("10")
            );

            // 実行
            var expected = target.selectByPrimaryKey(createEntity("10").getUsername());
            expected.setEmail("test");
            int count = target.updateByPrimaryKeyAndVersionSelective(expected);

            // 検証
            assertThat(count).isEqualTo(1);

            var actual = target.selectByPrimaryKey(expected.getUsername());

            // バージョンが＋１
            assertThat(actual.getVersion()).isEqualTo(expected.getVersion() + 1L);
        }

        @Test
        @DisplayName("[正常系]指定した主キーのデータがなければ0が返る。")
        void test002() {
            // 準備
            deleteAll();
            insertIntoDatabase(
                    createEntity("10")
            );

            // 実行
            var expected = createEntity("11");
            int actualCount = target.updateByPrimaryKeyAndVersionSelective(expected);

            // 検証
            assertThat(actualCount).isEqualTo(0);
        }

        @Test
        @DisplayName("[異常系]バージョンが正しくなければ楽観的排他制御エラー")
        void test003() {
            // 準備
            deleteAll();
            insertIntoDatabase(
                    createEntity("10")
            );

            // 実行
            var expected = target.selectByPrimaryKey(createEntity("10").getUsername());
            expected.setVersion(10L);
            int actualCount = target.updateByPrimaryKeyAndVersionSelective(expected);

            // 検証
            assertThat(actualCount).isEqualTo(0);
        }

    }

    @Nested
    class updateByPrimaryKeyAndVersion {

        @Test
        @DisplayName("[正常系]主キーを指定して更新できること、戻値は更新件数")
        void test001() {
            // 準備
            deleteAll();
            insertIntoDatabase(
                    createEntity("10")
            );

            // 実行
            var expected = target.selectByPrimaryKey(createEntity("10").getUsername());
            expected.setEmail("test");
            int count = target.updateByPrimaryKeyAndVersion(expected);

            // 検証
            assertThat(count).isEqualTo(1);

            var actual = target.selectByPrimaryKey(expected.getUsername());

            // バージョンが＋１
            assertThat(actual.getVersion()).isEqualTo(expected.getVersion() + 1L);
        }

        @Test
        @DisplayName("[正常系]指定した主キーのデータがなければ0が返る。")
        void test002() {
            // 準備
            deleteAll();
            insertIntoDatabase(
                    createEntity("10")
            );

            // 実行
            var expected = createEntity("11");
            int actualCount = target.updateByPrimaryKeyAndVersion(expected);

            // 検証
            assertThat(actualCount).isEqualTo(0);
        }

        @Test
        @DisplayName("[異常系]バージョンが正しくなければ楽観的排他制御エラー")
        void test003() {
            // 準備
            deleteAll();
            insertIntoDatabase(
                    createEntity("10")
            );

            // 実行
            var expected = target.selectByPrimaryKey(createEntity("10").getUsername());
            expected.setVersion(10L);
            int actualCount = target.updateByPrimaryKeyAndVersion(expected);

            // 検証
            assertThat(actualCount).isEqualTo(0);
        }

    }

}