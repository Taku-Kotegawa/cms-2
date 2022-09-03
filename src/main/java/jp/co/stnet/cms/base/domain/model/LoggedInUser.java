package jp.co.stnet.cms.base.domain.model;

import jp.co.stnet.cms.base.domain.model.mbg.Account;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.Collection;


/**
 * ログインユーザエンティティ
 */
@Getter
public class LoggedInUser extends User {

    private final AccountRole accountRole;

    private final LocalDateTime lastLoginDate;

    public LoggedInUser(AccountRole accountRole, boolean isLocked,
                        LocalDateTime lastLoginDate,
                        Collection<? extends GrantedAuthority> authorities) {

        super(accountRole.getUsername(), accountRole.getPassword(),
                true, true, true,
                !isLocked, authorities);

        this.accountRole = accountRole;
        this.lastLoginDate = lastLoginDate;
    }

}
