package jp.co.stnet.cms.base.application.service;

import jp.co.stnet.cms.base.application.repository.PermissionRoleRepository;
import jp.co.stnet.cms.base.domain.enums.Permission;
import jp.co.stnet.cms.base.domain.enums.Role;
import jp.co.stnet.cms.base.domain.model.mbg.PermissionRole;
import jp.co.stnet.cms.base.domain.model.mbg.PermissionRoleExample;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRoleRepository permissionRoleRepository;

    @Override
    @Transactional(readOnly = true)
    public Map<String, Map<String, Boolean>> findAllMap() {

        Map<String, Map<String, Boolean>> map = new LinkedHashMap<>();
        List<PermissionRole> permissionRoleList = permissionRoleRepository.findAllByExample(new PermissionRoleExample());

        for (Permission permission : Permission.values()) {
            Map<String, Boolean> item = new LinkedHashMap<>();
            for (Role role : Role.values()) {
                item.put(role.name(), existPermissionRole(permissionRoleList, permission, role));
            }
            map.put(permission.name(), item);
        }

        return map;
    }

    @Override
    public void deleteAll() {
        permissionRoleRepository.deleteByExample(null);
    }

    @Override
    public List<PermissionRole> saveAll(Map<String, Map<String, Boolean>> map) {
        deleteAll();
        List<PermissionRole> list = mapToList(map);
        return permissionRoleRepository.saveAll(list);
    }

    private List<PermissionRole> mapToList(Map<String, Map<String, Boolean>> map) {
        List<PermissionRole> list = new ArrayList<>();
        if (map != null) {
            for (Map.Entry<String, Map<String, Boolean>> permissionMap : map.entrySet()) {
                for (Map.Entry<String, Boolean> roleMap : permissionMap.getValue().entrySet()) {
                    if (roleMap.getValue()) {
                        PermissionRole item = new PermissionRole();
                        item.setPermission(Permission.valueOf(permissionMap.getKey()).name());
                        item.setRole(Role.valueOf(roleMap.getKey()).name());
                        list.add(item);
                    }
                }
            }
        }
        return list;
    }

    /**
     * パーミッション・ロールの組み合わせを検索する。
     *
     * @param permissionRoleList パーミッション・ロールの組み合わせ(リスト)
     * @param permission         パーミッションのコード
     * @param role               ロールのコード
     * @return true:存在する, false:存在しない
     */
    private boolean existPermissionRole(List<PermissionRole> permissionRoleList, Permission permission, Role role) {
        for (PermissionRole permissionRole : permissionRoleList) {
            if (Objects.equals(permissionRole.getPermission(), permission.getCodeValue())
                    && Objects.equals(permissionRole.getRole(), role.getCodeValue())) {
                return true;
            }
        }
        return false;
    }

}
