package jp.co.stnet.cms.example.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.stnet.cms.example.domain.model.SimpleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimpleEntityCsvDto implements Serializable {

    public static SimpleEntityCsvDto from(SimpleEntity simpleEntity) {
        var simpleEntityCsvDto = new SimpleEntityCsvDto();
        BeanUtils.copyProperties(simpleEntity, simpleEntityCsvDto);
        return simpleEntityCsvDto;
    }

    @JsonProperty(index = 1)
    private Long id;

    @JsonProperty(index = 2)
    private String createdBy;

    @JsonProperty(index = 3)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime createdDate;

    @JsonProperty(index = 4)
    private String lastModifiedBy;

    @JsonProperty(index = 5)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime lastModifiedDate;

    @JsonProperty(index = 6)
    private Long version;

    @JsonProperty(index = 7)
    private String attachedFile01uuid;

    @JsonProperty(index = 8)
    private String checkbox01;

    @JsonProperty(index = 9)
    private String combobox01;

    @JsonProperty(index = 10)
    private String combobox02;

    @JsonProperty(index = 11)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private LocalDate date01;

    @JsonProperty(index = 12)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime datetime01;

    @JsonProperty(index = 13)
    private Boolean radio01;

    @JsonProperty(index = 14)
    private String radio02;

    @JsonProperty(index = 15)
    private String select01;

    @JsonProperty(index = 16)
    private String select03;

    @JsonProperty(index = 17)
    private String status;

    @JsonProperty(index = 18)
    private String text01;

    @JsonProperty(index = 19)
    private Integer text02;

    @JsonProperty(index = 20)
    private Float text03;

    @JsonProperty(index = 21)
    private Short text04;

    @JsonProperty(index = 22)
    private String textarea01;

    @JsonProperty(index = 23)
    private List<String> text05;

    @JsonProperty(index = 24)
    private List<String> checkbox02;

    @JsonProperty(index = 25)
    private List<String> select02;

    @JsonProperty(index = 26)
    private List<String> select04;

    @JsonProperty(index = 27)
    private List<String> combobox03;

}
