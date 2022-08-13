package jp.co.stnet.cms.base.application.service;

import jp.co.stnet.cms.base.domain.enums.Status;
import jp.co.stnet.cms.base.domain.model.Account;
import liquibase.pro.packaged.A;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.annotation.SecurityTestExecutionListeners;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class AccountServiceTest {

    @Autowired
    AccountService target;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Nested
    public class save {

        @Test
        void test01_xxx(){

            var expected = Account.builder()
                    .username("abc")
                    .firstName("firstName")
                    .lastName("lastName")
                    .build();

            var result = target.save(expected);

            System.out.println(expected);
            System.out.println(result);

        }

    }

    @Nested
    public class valid {

        @Test
        void test_001() {
            var username = "valid_001";
            var expected = Account.builder()
                    .username(username)
                    .status(Status.INVALID.getCodeValue())
                    .build();

            target.save(expected);

            var result = target.valid(username);

            System.out.println(result);

        }

        @Test
        void test_002() {
            var username = "valid_002";
            var expected = Account.builder()
                    .username(username)
                    .status(Status.VALID.getCodeValue())
                    .build();

            target.save(expected);

            var result = target.valid(username);

            // 例外がスローされる

        }



    }

    @Nested
    public class invalid {

    }


    @Nested
    public class findById {

        @Test
        void test01_xxxx(){

            String id = "abc";
            Account result = target.findById(id);

            System.out.println(result);

        }

    }



}