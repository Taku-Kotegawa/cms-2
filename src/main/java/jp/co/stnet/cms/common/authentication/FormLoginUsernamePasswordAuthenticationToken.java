package jp.co.stnet.cms.common.authentication;

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * ログイン画面から入力される認証情報を格納するトークン
 */
@Getter
public class FormLoginUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {

    // 管理者としてログイン
    private final Boolean loginAsAdministrator;

    public FormLoginUsernamePasswordAuthenticationToken(Object principal, Object credentials, Boolean loginAsAdministrator) {
        super(principal, credentials);
        this.loginAsAdministrator = loginAsAdministrator;
    }

    public FormLoginUsernamePasswordAuthenticationToken(Object principal, Object credentials, Boolean loginAsAdministrator, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
        this.loginAsAdministrator = loginAsAdministrator;
    }
}
