package jp.co.stnet.cms.base.infrastructure.mapper;

import jp.co.stnet.cms.base.domain.enums.Status;
import jp.co.stnet.cms.base.domain.model.Account;
import jp.co.stnet.cms.base.domain.model.AccountExample;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//@Transactional
@SpringBootTest
class AccountMapperTest {

    @Autowired
    AccountMapper target;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void countByExample() {
        target.selectByExample(new AccountExample());
    }

    private Account createEntity(String username) {
        return Account.builder()
                .username(username)
                .firstName("firstName")
                .lastName("lastName")
                .apiKey("apiKey" + username)
                .status(Status.VALID.getCodeValue())
                .department("department")
                .email("email")
                .imageUuid("imageUuid")
                .password("password")
                .profile("profile")
                .url("url")
                .allowedIp("allowedIp")
                .build();
    }


    @Test
    void deleteByExample() {
    }

    @Test
    void deleteByPrimaryKey() {
    }

    @Test
    void insert() {
        target.insert(createEntity("insert-2"));

    }

    @Test
    void insertSelective() {
        target.insertSelective(
                Account.builder()
                        .username("abcde")
                        .firstName("abcde")
                        .build());
    }

    @Test
    void selectByExampleWithRowbounds() {
    }

    @Test
    void selectByExample() {
    }

    @Test
    void selectByPrimaryKey() {
    }

    @Test
    void updateByExampleSelective() {
    }

    @Test
    void updateByExample() {
    }

    @Test
    void updateByPrimaryKeyAndVersionSelective() {
    }

    @Test
    void updateByPrimaryKeySelective() {

        target.insert(createEntity("insert-4"));

        target.updateByPrimaryKeySelective(Account.builder()
                .username("insert-4")
                .version(1L)
                .build());
    }

    @Test
    void updateByPrimaryKeyAndVersion() {
        target.updateByPrimaryKeyAndVersion(Account.builder()
                .username("insert-2")
                .version(1L)
                .build());
    }

    @Test
    void updateByPrimaryKey() {
        target.updateByPrimaryKey(Account.builder()
                .username("abcde")
                .firstName("abcde")
                .build());
    }
}