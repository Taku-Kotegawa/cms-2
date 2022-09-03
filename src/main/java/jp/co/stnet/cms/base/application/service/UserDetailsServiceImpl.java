package jp.co.stnet.cms.base.application.service;

import jp.co.stnet.cms.base.domain.model.AccountRole;
import jp.co.stnet.cms.base.domain.model.LoggedInUser;
import jp.co.stnet.cms.base.domain.model.mbg.PermissionRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    AccountService accountService;

    @Autowired
    PermissionRoleService permissionRoleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {

//            AccountRole accountRole = accountService.findById(username);
            AccountRole accountRole = new AccountRole();
            accountRole.setUsername("admin");
            accountRole.setPassword("{pbkdf2}1dd84f42a7a9a173f8f806d736d34939bed6a36e2948e8bfe88801ee5e6e61b815efc389d03165a4");

//            if (accountRole == null || accountRole.getStatus().equals(Status.INVALID.getCodeValue())) {
//                throw new UsernameNotFoundException("user not found");
//            }

            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            List<String> roleIds = new ArrayList<>();

//            for (String roleLabel : accountRole.getRoles()) {
//
//                if (!roleLabel.equals(Role.ADMIN.name())) {
//                    authorities.add(new SimpleGrantedAuthority("ROLE_" + roleLabel));
//                    roleIds.add(roleLabel);
//                }
//            }

            for (PermissionRole permissionRole : permissionRoleService.findAllByRole(roleIds)) {
                authorities.add(new SimpleGrantedAuthority(permissionRole.getPermission()));
            }


            return new LoggedInUser(accountRole,
//                    accountService.isLocked(username),
//                    accountService.getLastLoginDate(username),
                    false,
                    LocalDateTime.now(),
                    authorities);

        } catch (ResourceNotFoundException e) {
            throw new UsernameNotFoundException("user not found", e);
        }
    }

}
