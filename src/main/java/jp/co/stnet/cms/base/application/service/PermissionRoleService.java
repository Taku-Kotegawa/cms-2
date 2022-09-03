package jp.co.stnet.cms.base.application.service;

import jp.co.stnet.cms.base.domain.model.mbg.PermissionRole;

import java.util.Collection;
import java.util.List;

/**
 * PermissionRoleSharedService
 */
public interface PermissionRoleService {

    /**
     * ロールのコレクションで検索する。
     *
     * @param roleIds ロールのコレクション
     * @return ヒットしたデータのリスト
     */
    List<PermissionRole> findAllByRole(Collection<String> roleIds);

}
