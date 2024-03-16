package jp.co.stnet.cms.equipment.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.stnet.cms.common.util.OperationsUtil;
import jp.co.stnet.cms.equipment.domain.model.OrganizationBean;
import jp.co.stnet.cms.equipment.domain.model.mbg.Organization;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrganizationListBean extends OrganizationBean {

    private String operations;

    private String DT_RowId;

    private String DT_RowClass;

    private Map<String, String> DT_RowAttr;

    public static OrganizationListBean of(Organization source) {
        var target = new OrganizationListBean();
        BeanUtils.copyProperties(source, target);

        target.setOperations(getOperations(source));
        target.setDT_RowId(source.getOrganizationId().toString()); // DT_RowIDにはユニークな値をセットする

        return target;
    }

    /**
     * 編集ボタンの組み立て
     *
     * @param source
     * @return
     */
    static String getOperations(Organization source) {
        var op = new OperationsUtil(null);
        return "<a href=\"" + op.getEditUrl(source.getOrganizationId().toString())
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