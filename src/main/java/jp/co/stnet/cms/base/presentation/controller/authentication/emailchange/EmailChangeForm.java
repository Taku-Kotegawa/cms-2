package jp.co.stnet.cms.base.presentation.controller.authentication.emailchange;

import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@Data
public class EmailChangeForm {

    /**
     * 新規メール
     */
    @Email
    @NotNull
    private String newEmail;
}
