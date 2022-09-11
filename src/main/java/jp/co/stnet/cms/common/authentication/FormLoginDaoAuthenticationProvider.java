package jp.co.stnet.cms.common.authentication;

import jp.co.stnet.cms.base.application.service.PermissionRoleService;
import jp.co.stnet.cms.base.domain.enums.Role;
import jp.co.stnet.cms.base.domain.model.Account;
import jp.co.stnet.cms.base.domain.model.LoggedInUser;
import jp.co.stnet.cms.base.domain.model.mbg.PermissionRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.util.matcher.IpAddressMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * ログイン画面のカスタマイズ
 */
public class FormLoginDaoAuthenticationProvider extends DaoAuthenticationProvider {

    @Autowired
    PermissionRoleService permissionRoleService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        super.additionalAuthenticationChecks(userDetails, authentication);
        allowedIpCheck(userDetails, authentication);
        loginAsAdminCheck(userDetails, authentication);
    }

    /**
     * 管理者としてログインの権限チェック
     * @param userDetails UserDetails
     * @param authentication Authentication
     */
    protected void loginAsAdminCheck(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication){
        LoggedInUser loggedInUser = (LoggedInUser) userDetails;
        Account account = loggedInUser.getAccount();

        FormLoginUsernamePasswordAuthenticationToken formLoginUsernamePasswordAuthenticationToken =
                (FormLoginUsernamePasswordAuthenticationToken) authentication;

        // ログイン画面の「Login As Administrator」にチェックが入っている場合、ADMINロールを保持している必要がある。
        if (formLoginUsernamePasswordAuthenticationToken.getLoginAsAdministrator()) {
            if (!account.getRoles().contains(Role.ADMIN.name())) {
                throw new BadCredentialsException(messages.getMessage(
                        "AbstractUserDetailsAuthenticationProvider.badCredentials",
                        "Bad credentials (loginAsAdmin)"));
            }
        }
    }


    /**
     * 接続元IPチェック
     * @param userDetails UserDetails
     * @param authentication Authentication
     */
    protected void allowedIpCheck(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) {
        LoggedInUser loggedInUser = (LoggedInUser) userDetails;
        Account account = loggedInUser.getAccount();

        // 接続元IPアドレスのチェック
        if (StringUtils.isNotBlank(account.getAllowedIp())) {

            WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
            String userIp = details.getRemoteAddress();

            String[] allowedIps = account.getAllowedIp().split(",");
            boolean userIpIsAllowed = false;

            for (String allowedIp : allowedIps) {
                if (matches(userIp, allowedIp)) {
                    userIpIsAllowed = true;
                }
            }

            if (!userIpIsAllowed) {
                throw new BadCredentialsException(messages.getMessage(
                        "AbstractUserDetailsAuthenticationProvider.badCredentials",
                        "Bad User IP Address"));
            }
        }
    }

    @Override
    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {

        FormLoginUsernamePasswordAuthenticationToken formLoginUsernamePasswordAuthenticationToken =
                (FormLoginUsernamePasswordAuthenticationToken) authentication;
        boolean loginAsAdministrator = formLoginUsernamePasswordAuthenticationToken.getLoginAsAdministrator();

        LoggedInUser loggedInUser = (LoggedInUser) user;
        Account account = loggedInUser.getAccount();
        Collection<GrantedAuthority> authorities = new HashSet<>();
        List<String> roleIds = new ArrayList<>();
        for (String roleLabel : account.getRoles()) {
            // Administrator権限でログインしない場合、ADMINロールを除外
            // asAdmin=true, role=admin -> true
            // asAdmin=false, role=admin -> false
            // asAdmin=true, role=other -> true
            // asAdmin=false, role=other -> true
            if (!(!formLoginUsernamePasswordAuthenticationToken.getLoginAsAdministrator() && roleLabel.equals(Role.ADMIN.name()))) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + roleLabel));
                roleIds.add(roleLabel);
            }
        }

        for (PermissionRole permissionRole : permissionRoleService.findAllByRole(roleIds)) {
            authorities.add(new SimpleGrantedAuthority(permissionRole.getPermission()));
        }

        principal = new LoggedInUser(account,
                !loggedInUser.isAccountNonLocked(),
                loggedInUser.getLastLoginDate(),
                authorities);

        return new FormLoginUsernamePasswordAuthenticationToken(principal,
                authentication.getCredentials(), loginAsAdministrator,
                authorities);

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return FormLoginUsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }


    private boolean matches(String ip, String subnet) {
        if ("0:0:0:0:0:0:0:1".equals(ip) || "127.0.0.1".equals(ip)) {
            return true;
        }
        IpAddressMatcher ipAddressMatcher = new IpAddressMatcher(subnet);
        return ipAddressMatcher.matches(ip);
    }
}
