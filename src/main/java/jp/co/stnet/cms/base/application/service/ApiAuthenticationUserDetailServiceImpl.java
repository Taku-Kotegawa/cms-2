package jp.co.stnet.cms.base.application.service;


import jp.co.stnet.cms.base.domain.model.AccountRole;
import jp.co.stnet.cms.base.domain.model.LoggedInUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ApiAuthenticationUserDetailServiceImpl implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    @Autowired
    AccountService accountService;

    @Autowired
    PermissionRoleService permissionRoleService;

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {

        // フィルタで取得したAuthorizationヘッダの値
        String credential = token.getCredentials().toString();

        credential = credential.replace("Bearer ", "");

        // 空の場合は認証エラーとする
        if (credential.isEmpty()) {
            throw new UsernameNotFoundException("Authorization header must not be empty.");
        }

//        Account account = accountService.findByApiKey(credential);
//
//        if (account == null) {
//            throw new UsernameNotFoundException("Invalid authorization header.");
//        }
//
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        List<String> roleIds = new ArrayList<>();
//
//        for (String roleLabel : account.getRoles()) {
//            authorities.add(new SimpleGrantedAuthority("ROLE_" + roleLabel));
//            roleIds.add(roleLabel);
//        }
//
//        for (PermissionRole permissionRole : permissionRoleService.findAllByRole(roleIds)) {
//            authorities.add(new SimpleGrantedAuthority(permissionRole.getPermission().name()));
//        }
//
//
//        return new LoggedInUser(new account,
//                accountService.isLocked(account.getUsername()),
//                accountService.getLastLoginDate(account.getUsername()),
//                authorities);
//

        var accountRole = new AccountRole();
        accountRole.setUsername("admin");
        accountRole.setPassword("{pbkdf2}1dd84f42a7a9a173f8f806d736d34939bed6a36e2948e8bfe88801ee5e6e61b815efc389d03165a4");

        return new LoggedInUser(
                accountRole,
                false,
                LocalDateTime.now(),
                authorities);

    }
}