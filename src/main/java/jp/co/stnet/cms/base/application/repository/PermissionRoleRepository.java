package jp.co.stnet.cms.base.application.repository;

import jp.co.stnet.cms.base.domain.model.mbg.PermissionRole;
import jp.co.stnet.cms.base.domain.model.mbg.PermissionRoleExample;
import jp.co.stnet.cms.base.domain.model.mbg.PermissionRoleKey;
import jp.co.stnet.cms.base.domain.model.mbg.TRole;
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

    @Override
    PermissionRoleExample example() {
        return new PermissionRoleExample();
    }

    public List<PermissionRole> findByRoleIn(Collection<TRole> roles) {
        if (roles == null || roles.isEmpty()) {
            return new ArrayList<>();
        }

        var example = new PermissionRoleExample();
        example.or().andRoleIn(roleToString(roles));
        return mapper.selectByExample(example);
    }

    protected List<String> roleToString(Collection<TRole> roles) {
        return roles.stream().map(TRole::getRole).toList();
    }

}
