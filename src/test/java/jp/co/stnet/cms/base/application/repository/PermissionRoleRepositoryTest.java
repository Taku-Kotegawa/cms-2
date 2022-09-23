package jp.co.stnet.cms.base.application.repository;

import jp.co.stnet.cms.base.domain.model.mbg.PermissionRole;
import jp.co.stnet.cms.base.domain.model.mbg.PermissionRoleExample;
import jp.co.stnet.cms.base.domain.model.mbg.PermissionRoleKey;
import jp.co.stnet.cms.base.infrastructure.mapper.MapperInterface;
import jp.co.stnet.cms.base.infrastructure.mapper.mbg.PermissionRoleMapper;
import org.junit.jupiter.api.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.rightPad;

@SpringBootTest
//@Transactional
class PermissionRoleRepositoryTest extends AbstractRepositoryStringIdTest<PermissionRole, PermissionRoleExample, PermissionRoleKey> {

    @Autowired
    PermissionRoleRepository target;

    @Autowired
    PermissionRoleMapper mapper;

    @Override
    AbstractRepository<PermissionRole, PermissionRoleExample, PermissionRoleKey> target() {
        return target;
    }

    @Override
    MapperInterface<PermissionRole, PermissionRoleExample, PermissionRoleKey> mapper() {
        return mapper;
    }

    @Override
    PermissionRole createEntity(String id) {
        return PermissionRole.builder()
                .role(rightPad("role:" + id, 255, "0"))
                .permission(rightPad("permission:" + id, 255, "0"))
                .dummy("x")
                .build();
    }


    protected PermissionRoleExample newExample() {
        return new PermissionRoleExample();
    }

    protected void deleteAll() {
        mapper().deleteByExample(null);
    }

    @Override
    void setOverflowValue(PermissionRole entity) {
        entity.setRole(entity.getRole() + "a");
    }

    @Override
    void changeField(PermissionRole entity) {
        entity.setDummy("Y");
    }

    @Override
    void fixVersion(PermissionRole entity) {
        // 該当なし
    }

    @Override
    void fixVersionList(List<PermissionRole> entities) {
        // 該当なし
    }

    @Override
    void setFindByCondition(PermissionRoleExample example) {
        example.or().andRoleEqualTo(createEntity("01").getRole());
    }

    @Override
    void setNotFindByCondition(PermissionRoleExample example) {
        example.or().andRoleEqualTo("not exist");
    }

    @Override
    void setOrderByClause(PermissionRoleExample example) {
        example.setOrderByClause("role desc");
    }


    @Nested
    class findByRoleIn {
    }
}