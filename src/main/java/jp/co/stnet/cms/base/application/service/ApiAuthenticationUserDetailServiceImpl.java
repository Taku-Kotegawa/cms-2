package jp.co.stnet.cms.base.application.service;


import jp.co.stnet.cms.base.domain.model.Account;
import jp.co.stnet.cms.base.domain.model.LoggedInUser;
import jp.co.stnet.cms.base.domain.model.mbg.PermissionRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;

import java.util.ArrayList;

public class ApiAuthenticationUserDetailServiceImpl implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountSharedService accountSharedService;

    @Autowired
    PermissionRoleService permissionRoleService;

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {

        // フィルタで取得したAuthorizationヘッダの値
        var credential = token.getCredentials().toString().replace("Bearer ", "");

        // 空の場合は認証エラーとする
        if (credential.isEmpty()) {
            throw new UsernameNotFoundException("Authorization header must not be empty.");
        }

        Account account;
        try {
            account = accountService.findByApiKey(credential);
        } catch (ResourceNotFoundException e) {
            throw new UsernameNotFoundException("Invalid authorization header.");
        }

        var authorities = new ArrayList<SimpleGrantedAuthority>();
        var roleIds = new ArrayList<String>();

        for (var roleLabel : account.getRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + roleLabel));
            roleIds.add(roleLabel);
        }

        for (PermissionRole permissionRole : permissionRoleService.findAllByRole(roleIds)) {
            authorities.add(new SimpleGrantedAuthority(permissionRole.getPermission()));
        }

        return new LoggedInUser(
                account,
                accountSharedService.isLocked(account.getUsername()),
                accountSharedService.getLastLoginDate(account.getUsername()),
                authorities);
    }
}