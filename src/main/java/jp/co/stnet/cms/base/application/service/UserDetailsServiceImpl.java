package jp.co.stnet.cms.base.application.service;

import jp.co.stnet.cms.base.domain.enums.Role;
import jp.co.stnet.cms.base.domain.enums.Status;
import jp.co.stnet.cms.base.domain.model.Account;
import jp.co.stnet.cms.base.domain.model.LoggedInUser;
import jp.co.stnet.cms.base.domain.model.mbg.PermissionRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountSharedService accountSharedService;
    private final PermissionRoleService permissionRoleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {

            Account account = accountSharedService.findOne(username);

            if (account == null || account.getStatus().equals(Status.INVALID.getCodeValue())) {
                throw new UsernameNotFoundException("user not found");
            }

            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            List<String> roleIds = new ArrayList<>();

            for (String roleLabel : account.getRoles()) {
                if (!roleLabel.equals(Role.ADMIN.name())) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + roleLabel));
                    roleIds.add(roleLabel);
                }
            }

            for (PermissionRole permissionRole : permissionRoleService.findAllByRole(roleIds)) {
                authorities.add(new SimpleGrantedAuthority(permissionRole.getPermission()));
            }


            return new LoggedInUser(account,
                    accountSharedService.isLocked(username),
                    accountSharedService.getLastLoginDate(username),
                    authorities);

        } catch (ResourceNotFoundException e) {
            throw new UsernameNotFoundException("user not found", e);
        }
    }

}
