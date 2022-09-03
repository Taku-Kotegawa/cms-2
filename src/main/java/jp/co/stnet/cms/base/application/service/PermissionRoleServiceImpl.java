package jp.co.stnet.cms.base.application.service;

import jp.co.stnet.cms.base.application.repository.PermissionRoleRepository;
import jp.co.stnet.cms.base.domain.enums.Role;
import jp.co.stnet.cms.base.domain.model.mbg.PermissionRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@Transactional
public class PermissionRoleServiceImpl implements PermissionRoleService {

    @Autowired
    PermissionRoleRepository permissionRoleRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PermissionRole> findAllByRole(Collection<String> roleIds) {

        Collection<Role> roleList = new ArrayList<>();
        for (String roleId : roleIds) {
            roleList.add(Role.valueOf(roleId));
        }

        return permissionRoleRepository.findByRoleIn(roleList);
    }

}
