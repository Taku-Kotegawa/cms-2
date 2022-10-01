package jp.co.stnet.cms.example.presentation.dto;

import jp.co.stnet.cms.example.domain.model.SimpleEntity;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
class SimpleEntityFlatDtoTest {

    @Autowired
    ModelMapper mapper;

    @Test
    void test001() {

        var source = SimpleEntity.builder()
                .text05(List.of("a", "b", "c"))
                .build();

        var destination = mapper.map(source, SimpleEntityFlatDto.class);

        assertThat(destination.getText05()).isEqualTo("a,b,c");

    }

}