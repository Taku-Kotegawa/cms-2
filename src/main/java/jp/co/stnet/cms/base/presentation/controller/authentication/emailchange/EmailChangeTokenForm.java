package jp.co.stnet.cms.base.presentation.controller.authentication.emailchange;

import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class EmailChangeTokenForm {

    @NotNull
    private String token;

    @NotNull
    private String secret;
}
