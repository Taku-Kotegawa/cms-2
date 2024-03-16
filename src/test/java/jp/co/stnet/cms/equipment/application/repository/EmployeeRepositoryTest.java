package jp.co.stnet.cms.equipment.application.repository;
import java.time.LocalDateTime;

import jp.co.stnet.cms.equipment.domain.model.mbg.Employee;
import jp.co.stnet.cms.equipment.domain.model.mbg.Position;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository target;

    @Nested
    class mapper {
    }

    @Nested
    class findById2 {
    }

    @Nested
    class findPageByInput {
    }

    @Nested
    class example {
    }

}
