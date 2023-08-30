package jp.co.stnet.cms.equipment.domain.model;

import jp.co.stnet.cms.base.domain.enums.Status;
import jp.co.stnet.cms.equipment.domain.model.mbg.Employee;
import jp.co.stnet.cms.equipment.domain.model.mbg.VEmployee;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.BeanUtils;

import java.util.Objects;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class EmployeeBean extends Employee {

    /**
     * コピー元の値を引き継いだオブジェクトの作成
     *
     * @param source
     * @return
     */
    public static EmployeeBean of(VEmployee source) {
        var target = new EmployeeBean();
        BeanUtils.copyProperties(source, target);

        target.setStatusLabel(Objects.requireNonNull(Status.getByValue(source.getStatus())).getCodeLabel());

        target.setGroupName(source.getGroupName());
        target.setPositionName(source.getPositionName());

        return target;
    }

    /**
     * ステータス(ラベル)
     */
    private String statusLabel;

    /**
     * 所属部署名
     */
    private String groupName;

    /**
     * 役職名
     */
    private String positionName;


}
