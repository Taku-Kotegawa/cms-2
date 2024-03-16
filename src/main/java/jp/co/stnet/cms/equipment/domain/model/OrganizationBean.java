package jp.co.stnet.cms.equipment.domain.model;

import jp.co.stnet.cms.base.domain.enums.Status;
import jp.co.stnet.cms.equipment.domain.model.mbg.Organization;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.BeanUtils;

import java.util.Objects;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class OrganizationBean extends Organization {

    /**
     * ステータス(ラベル)
     */
    private String statusLabel;

    /**
     * 所属用氏名
     */
    private String empName;


    /**
     * コピー元の値を引き継いだオブジェクトの作成
     *
     * @param source
     * @return
     */
    public static OrganizationBean of(Organization source) {
        var target = new OrganizationBean();
        BeanUtils.copyProperties(source, target);
        target.setStatusLabel(Objects.requireNonNull(Status.getByValue(source.getStatus())).getCodeLabel());

        // TODO 所属長氏名を取得する

        return target;
    }

}
