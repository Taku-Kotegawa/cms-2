package jp.co.stnet.cms.equipment.domain.model;

import jp.co.stnet.cms.base.domain.enums.Status;
import jp.co.stnet.cms.equipment.domain.model.mbg.Position;
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
public class PositionBean extends Position {

    /**
     * ステータス(ラベル)
     */
    private String statusLabel;

    /**
     * コピー元の値を引き継いだオブジェクトの作成
     *
     * @param source
     * @return
     */
    public static PositionBean of(Position source) {
        var target = new PositionBean();
        BeanUtils.copyProperties(source, target);
        target.setStatusLabel(Objects.requireNonNull(Status.getByValue(source.getStatus())).getCodeLabel());
        return target;
    }

}
