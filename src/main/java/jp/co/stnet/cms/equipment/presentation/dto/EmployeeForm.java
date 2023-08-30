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
public class EmployeeForm implements Serializable {

    /**
     * 従業員ID
     */
    @NotNull
    private String empId;

    /**
     * バージョン(排他制御用)
     */
    @NotNull(groups = Update.class)
    private Long version;

    /**
     * ステータス
     */
    @NotNull
    private String status;

    /**
     * 従業員氏名
     */
    @NotNull
    private String empName;

    /**
     * パスワード
     */
    @NotNull
    private String password;

    /**
     * メールアドレス
     */
    private String email;

    /**
     * 所属部署
     */
    private Long organizationId;

    /**
     * 役職
     */
    private Long positionId;

    public interface Create {
    }

    public interface Update {
    }

}