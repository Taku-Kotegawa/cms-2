package jp.co.stnet.cms.example.domain.model;

import jp.co.stnet.cms.example.domain.model.mbg.LineItem;
import jp.co.stnet.cms.example.domain.model.mbg.TSimpleEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@ToString(callSuper = true)
@NoArgsConstructor
public class SimpleEntity extends TSimpleEntity {

    /**
     * テキストフィールド(複数の値)
     */
    @Builder.Default
    private List<String> text05 = new ArrayList<>();

    /**
     * チェックボックス(複数の値)
     */
    @Builder.Default
    private List<String> checkbox02 = new ArrayList<>();

    /**
     * セレクト(複数の値)
     */
    @Builder.Default
    private List<String> select02 = new ArrayList<>();

    /**
     * セレクト(複数の値, select2)
     */
    @Builder.Default
    private List<String> select04 = new ArrayList<>();

    /**
     * コンボボックス(複数の値, Select2)
     */
    @Builder.Default
    private List<String> combobox03 = new ArrayList<>();

    /**
     * 明細行
     */
    @Builder.Default
    private List<LineItem> lineItems = new ArrayList<>();

}
