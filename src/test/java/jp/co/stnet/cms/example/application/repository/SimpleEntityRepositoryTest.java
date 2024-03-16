package jp.co.stnet.cms.example.application.repository;

import jp.co.stnet.cms.base.domain.enums.Status;
import jp.co.stnet.cms.common.datatables.Column;
import jp.co.stnet.cms.common.datatables.DataTablesInput;
import jp.co.stnet.cms.common.datatables.Search;
import jp.co.stnet.cms.example.domain.model.SimpleEntity;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.rightPad;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class SimpleEntityRepositoryTest {

    @Autowired
    SimpleEntityRepository target;


    SimpleEntity createEntity(String id) {
        return SimpleEntity.builder()
                .status(Status.VALID.getCodeValue())
                .text01(rightPad("test01:" + id, 255, "0"))
                .checkbox02(List.of("01", "02"))
                .combobox03(List.of("11", "12", "13", "14"))
                .select02(List.of("21", "22"))
                .select04(List.of("31", "32", "33"))
                .text05(List.of("41", "42", "43"))

                .build();
    }

    @Test
    void deleteAll() {
        target.deleteAll();
    }


    @Test
    void prepare() {
        target.register(createEntity("01"));
        target.register(createEntity("02"));
        target.register(createEntity("03"));
        target.register(createEntity("04"));
        target.register(createEntity("05"));
        target.register(createEntity("06"));
        target.register(createEntity("07"));
        target.register(createEntity("08"));
        target.register(createEntity("09"));
        target.register(createEntity("10"));
        target.register(createEntity("11"));
        target.register(createEntity("12"));
        target.register(createEntity("13"));
        target.register(createEntity("14"));
        target.register(createEntity("15"));
        target.register(createEntity("16"));
        target.register(createEntity("16"));
        target.register(createEntity("18"));
        target.register(createEntity("19"));
        target.register(createEntity("20"));
        target.register(createEntity("21"));
        target.register(createEntity("22"));
    }


    @Nested
    class register {

        @Test
        void test001() {
            // 準備
            deleteAll();
            var expected = createEntity("01");

            // 実行
            var actual = target.register(expected);

            // 検証
//            assertThat(actual).isEqualTo(expected);
        }

    }

    @Nested
    class findPageByInput {

        @Test
        void test001() {
            var input = DataTablesInput.builder()
                    .draw(1)
                    .start(0)
                    .length(20)
                    .search(Search.builder()
                            .regex(false)
                            .build())
                    .order(new ArrayList<>())
                    .columns(List.of(
                            Column.builder()
                                    .data("text01")
                                    .name(null)
                                    .searchable(true)
                                    .orderable(true)
                                    .search(Search.builder().regex(false).build())
                                    .build()
                    ))
                    .build();

            var actual = target.findPageByInput(input);

            assertThat(actual).isNotNull();

        }

    }

}