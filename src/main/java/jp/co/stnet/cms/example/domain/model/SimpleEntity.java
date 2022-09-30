package jp.co.stnet.cms.example.domain.model;

import jp.co.stnet.cms.example.domain.model.mbg.LineItem;
import jp.co.stnet.cms.example.domain.model.mbg.TSimpleEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

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
    private List<String> text05;

    /**
     * チェックボックス(複数の値)
     */
    private List<String> checkbox02;

    /**
     * セレクト(複数の値)
     */
    private List<String> select02;

    /**
     * セレクト(複数の値, select2)
     */
    private List<String> select04;

    /**
     * コンボボックス(複数の値, Select2)
     */
    private List<String> combobox03;

    /**
     * 添付ファイル(FileManaged)
     */
    private String attachedFile01Uuid;

    /**
     * 明細行
     */
    private List<LineItem> lineItems;


}
