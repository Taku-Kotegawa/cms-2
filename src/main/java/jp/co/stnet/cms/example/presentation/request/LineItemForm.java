package jp.co.stnet.cms.example.presentation.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class LineItemForm {

    @NotNull
    private String itemName;

    @NotNull
    @Min(1)
    private Integer itemNumber;

    @NotNull
    @Min(0)
    private Integer unitPrise;

}
