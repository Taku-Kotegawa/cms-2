package jp.co.stnet.cms.base.application.repository;

import jp.co.stnet.cms.base.domain.enums.Role;
import jp.co.stnet.cms.base.domain.model.mbg.PermissionRole;
import jp.co.stnet.cms.base.domain.model.mbg.PermissionRoleExample;
import jp.co.stnet.cms.base.domain.model.mbg.PermissionRoleKey;
import jp.co.stnet.cms.base.infrastructure.mapper.MapperInterface;
import jp.co.stnet.cms.base.infrastructure.mapper.mbg.PermissionRoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Component
public class PermissionRoleRepository extends AbstractRepository<PermissionRole, PermissionRoleExample, PermissionRoleKey> {

    private final PermissionRoleMapper mapper;

    @Override
    MapperInterface<PermissionRole, PermissionRoleExample, PermissionRoleKey> mapper() {
        return mapper;
    }

    public List<PermissionRole> findByRoleIn(Collection<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            return new ArrayList<PermissionRole>();
        }

        var example = new PermissionRoleExample();
        example.or().andRoleIn(roleToString(roles));
        return mapper.selectByExample(example);
    }

    protected List<String> roleToString(Collection<Role> roles) {
        List<String> list = new ArrayList<>();
        for (Role role : roles) {
            list.add(role.getCodeValue());
        }
        return list;
    }
}
