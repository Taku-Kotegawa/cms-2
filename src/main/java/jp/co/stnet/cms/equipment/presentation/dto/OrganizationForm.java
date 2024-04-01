package jp.co.stnet.cms.equipment.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationForm implements Serializable {

    /**
     * 部署ID
     */
    @NotNull(groups = Update.class)
    private Long organizationId;

    /**
     * 部署名
     */
    @NotNull
    private String groupName;

    /**
     * 親部署ID
     */
    @NotNull
    private Long parentId;

    /**
     * 部門長ID
     */
    private Long directorId;

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