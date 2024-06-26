package jp.co.stnet.cms.base.infrastructure.mapper;

import jp.co.stnet.cms.common.datatables.DataTablesInput;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class AccountQueryMapperTest {

    @Autowired
    AccountQueryMapper target;

//    @Test
//    void findById() {
//
//        var actual = target.findById("1000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
//        System.out.println(actual);
//
//    }

//    @Test
//    void findPage() {
//        var dataTablesInput = new DataTablesInput();
//        var pageable = PageRequest.of(1, 3);
//        var actual = target.findByInput(dataTablesInput, pageable);
//        assertThat(actual).size().isEqualTo(3);
//        assertThat(actual.get(0).getUsername()).isEqualTo("0400000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
//        assertThat(actual.get(0).getRoles()).size().isEqualTo(10);
//    }

}