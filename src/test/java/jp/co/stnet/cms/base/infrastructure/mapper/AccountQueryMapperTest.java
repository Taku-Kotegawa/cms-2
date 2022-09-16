package jp.co.stnet.cms.base.infrastructure.mapper;

import jp.co.stnet.cms.common.datatables.DataTablesInput;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountQueryMapperTest {

    @Autowired
    AccountQueryMapper target;

    @Test
    void findById() {

        var actual = target.findById("1000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
        System.out.println(actual);

    }

    @Test
    void findPage() {

        var dataTablesInput = new DataTablesInput();
        var pageable = PageRequest.of(0, 20);

        var actual = target.findPage(dataTablesInput, pageable);
        System.out.println(actual);
    }
}