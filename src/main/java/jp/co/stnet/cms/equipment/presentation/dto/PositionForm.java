package jp.co.stnet.cms.equipment.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PositionForm implements Serializable {

    /**
     * 役職ID
     */
    @NotNull(groups = Update.class)
    private Long positionId;

    /**
     * 役割名
     */
    @NotNull
    private String positionName;

    /**
     * ステータス
     */
    @NotNull
    private String status;

    /**
     * バージョン
     */
    @NotNull(groups = Update.class)
    private Long version;

    public interface Create {
    }

    public interface Update {
    }

}