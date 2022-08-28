package jp.co.stnet.cms.base.application.repository;

import jp.co.stnet.cms.base.domain.model.mbg.PermissionRole;
import jp.co.stnet.cms.base.domain.model.mbg.PermissionRoleExample;
import jp.co.stnet.cms.base.domain.model.mbg.PermissionRoleKey;
import jp.co.stnet.cms.base.infrastructure.mapper.MapperInterface;
import jp.co.stnet.cms.base.infrastructure.mapper.mbg.PermissionRoleMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Transactional
@Component
public class PermissionRoleRepository extends AbstractRepository<PermissionRole, PermissionRoleExample, PermissionRoleKey> {

    private final PermissionRoleMapper mapper;

    @Override
    MapperInterface<PermissionRole, PermissionRoleExample, PermissionRoleKey> mapper() {
        return mapper;
    }
}
