package jp.co.stnet.cms.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import jp.co.stnet.cms.example.presentation.dto.SimpleEntityCsvDto;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

class JacksonCsvUtilsTest {

    JacksonCsvUtils target;

    @Before
    void setUp() {
        target = new JacksonCsvUtils();
    }

    private List<SimpleEntityCsvDto> getData() {
        return List.of(
                createRecord(1L),
                createRecord(2L),
                createRecord(3L),
                createRecord(4L),
                createRecord(5L),
                createRecord(6L)
        );
    }

    private SimpleEntityCsvDto createRecord(Long id) {
        var record = SimpleEntityCsvDto.builder()
                .id(id)
                .version(id)
                .text01("TEXT01")
                .text02(2)
                .text03(1.1F)
                .text04(true)
                .text05(List.of("a", "b", "c"))
                .createdDate(LocalDateTime.now())
                .build();
        return record;
    }


    @Test
    void test001() throws JsonProcessingException {

        System.out.println(getData());

        System.out.println(JacksonCsvUtils.write(SimpleEntityCsvDto.class, getData()));

    }


}