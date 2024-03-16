package jp.co.stnet.cms.equipment.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.stnet.cms.common.util.OperationsUtil;
import jp.co.stnet.cms.equipment.domain.model.PositionBean;
import jp.co.stnet.cms.equipment.domain.model.mbg.Position;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PositionListBean extends PositionBean {

    private String operations;

    private String DT_RowId;

    private String DT_RowClass;

    private Map<String, String> DT_RowAttr;

    public static PositionListBean of(Position source) {
        var target = new PositionListBean();
        BeanUtils.copyProperties(source, target);

        target.setOperations(getOperations(source));
        target.setDT_RowId(source.getPositionId().toString()); // DT_RowIDにはユニークな値をセットする

        return target;
    }

    /**
     * 編集ボタンの組み立て
     *
     * @param source
     * @return
     */
    static String getOperations(Position source) {
        var op = new OperationsUtil(null);
        return "<a href=\"" + op.getEditUrl(source.getPositionId().toString())
                + "\" class=\"btn btn-button btn-sm\" style=\"white-space: nowrap\">"
                + op.getLABEL_EDIT() + "</a>";
    }

    @JsonProperty("DT_RowId")
    public String getDT_RowId() {
        return DT_RowId;
    }

    @JsonProperty("DT_RowClass")
    public String getDT_RowClass() {
        return DT_RowClass;
    }

    @JsonProperty("DT_RowAttr")
    public Map<String, String> getDT_RowAttr() {
        return DT_RowAttr;
    }

}