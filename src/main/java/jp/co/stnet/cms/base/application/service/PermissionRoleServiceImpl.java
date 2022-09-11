package jp.co.stnet.cms.base.application.service;

import jp.co.stnet.cms.base.application.repository.PermissionRoleRepository;
import jp.co.stnet.cms.base.domain.enums.Role;
import jp.co.stnet.cms.base.domain.model.mbg.PermissionRole;
import jp.co.stnet.cms.base.domain.model.mbg.TRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class PermissionRoleServiceImpl implements PermissionRoleService {

    private final PermissionRoleRepository permissionRoleRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PermissionRole> findAllByRole(Collection<String> roleIds) {
        Collection<TRole> roleList = new ArrayList<>();
        for (String roleId : roleIds) {
            roleList.add(TRole.builder().role(roleId).build());
        }
        return permissionRoleRepository.findByRoleIn(roleList);
    }

}
