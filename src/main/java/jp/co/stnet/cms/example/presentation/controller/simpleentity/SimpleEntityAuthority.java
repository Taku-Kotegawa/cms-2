package jp.co.stnet.cms.example.presentation.controller.simpleentity;

import jp.co.stnet.cms.base.domain.enums.Permission;
import jp.co.stnet.cms.base.domain.model.LoggedInUser;
import jp.co.stnet.cms.common.constant.Constants;
import jp.co.stnet.cms.example.domain.model.SimpleEntity;
import lombok.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

@Component
public class SimpleEntityAuthority {

    // 許可されたOperation
    private static final Set<String> allowedOperation = Set.of(
            Constants.OPERATION.CREATE,
            Constants.OPERATION.UPDATE,
            Constants.OPERATION.DELETE,
            Constants.OPERATION.SAVE,
//            Constants.OPERATION.SAVE_DRAFT,
//            Constants.OPERATION.CANCEL_DRAFT,
            Constants.OPERATION.INVALID,
            Constants.OPERATION.VALID,
            Constants.OPERATION.UPLOAD,
            Constants.OPERATION.VIEW,
            Constants.OPERATION.LIST,
            Constants.OPERATION.DOWNLOAD
    );

    /**
     * 権限チェックを行う。
     *
     * @param operation    操作の種類(Constants.OPERATIONに登録された値)
     * @param loggedInUser ログインユーザ情報
     * @return true=操作する権限を持つ, false=操作する権限なし
     * @throws AccessDeniedException    @PostAuthorizeを用いてfalse時にスロー
     * @throws IllegalArgumentException 不正なOperationが指定された場合
     * @throws NullPointerException     operation, loggedInUser がnullの場合
     */
    @PostAuthorize("returnObject == true")
    public Boolean hasAuthority(String operation, LoggedInUser loggedInUser) {
        return hasAuthority(operation, loggedInUser, null);
    }

    /**
     * 権限チェックを行い、権限がない場合はAccessDeniedExceptionをスローする
     *
     * @param operation    操作の種類(Constants.OPERATIONに登録された値)
     * @param loggedInUser ログインユーザ情報
     * @param simpleEntity エンティティ
     * @return true=操作する権限を持つ, 例外=権限を持たない場合
     * @throws AccessDeniedException    権限がない場合(@PostAuthorizeを用いて戻り値false時にスロー)
     * @throws IllegalArgumentException 不正なOperationが指定された場合
     * @throws NullPointerException     operation, loggedInUser がnullの場合
     */
    @PostAuthorize("returnObject == true")
    public Boolean hasAuthority(String operation, LoggedInUser loggedInUser, SimpleEntity simpleEntity) {
        return hasAuthorityWOException(operation, loggedInUser, simpleEntity);
    }

    /**
     * 権限の有無チェック
     *
     * @param operation    操作の種類(Constants.OPERATIONに登録された値)
     * @param loggedInUser ログインユーザ情報
     * @param simpleEntity エンティティ
     * @return true=操作する権限を持つ, false=権限を持たない
     * @throws IllegalArgumentException 不正なOperationが指定された場合
     * @throws NullPointerException     operation, loggedInUser がnullの場合
     */
    public Boolean hasAuthorityWOException(@NonNull String operation, @NonNull LoggedInUser loggedInUser, SimpleEntity simpleEntity) {

        // 入力チェック
        validate(operation);

        Collection<GrantedAuthority> authorities = loggedInUser.getAuthorities();
        if (authorities == null) {
            return false;
        }

        // 新規登録
        if (Constants.OPERATION.CREATE.equals(operation)) {
            return authorities.contains(new SimpleGrantedAuthority(Permission.SENTITY_CREATE.name()));
        }

        // 編集画面を開く
        else if (Constants.OPERATION.UPDATE.equals(operation)) {
            return authorities.contains(new SimpleGrantedAuthority(Permission.SENTITY_UPDATE.name()));
        }

        // 保存
        else if (Constants.OPERATION.SAVE.equals(operation)) {
            return authorities.contains(new SimpleGrantedAuthority(Permission.SENTITY_SAVE.name()));
        }

        // 下書き保存
        else if (Constants.OPERATION.SAVE_DRAFT.equals(operation)) {
            return authorities.contains(new SimpleGrantedAuthority(Permission.SENTITY_SAVE_DRAFT.name()));
        }

        // 下書き取消
        else if (Constants.OPERATION.CANCEL_DRAFT.equals(operation)) {
            return authorities.contains(new SimpleGrantedAuthority(Permission.SENTITY_SAVE_DRAFT.name()));
        }

        // 無効
        else if (Constants.OPERATION.INVALID.equals(operation)) {
            return authorities.contains(new SimpleGrantedAuthority(Permission.SENTITY_INVALID.name()));
        }

        // 無効解除
        else if (Constants.OPERATION.VALID.equals(operation)) {
            return authorities.contains(new SimpleGrantedAuthority(Permission.SENTITY_INVALID.name()));
        }

        // 削除
        else if (Constants.OPERATION.DELETE.equals(operation)) {
            return authorities.contains(new SimpleGrantedAuthority(Permission.SENTITY_DELETE.name()));
        }

        // アップロード
        else if (Constants.OPERATION.UPLOAD.equals(operation)) {
            return authorities.contains(new SimpleGrantedAuthority(Permission.SENTITY_UPLOAD.name()));
        }

        // ダウンロード
        else if (Constants.OPERATION.DOWNLOAD.equals(operation)) {
            return (
                    authorities.contains(new SimpleGrantedAuthority(Permission.SENTITY_CREATE.name()))
                            || authorities.contains(new SimpleGrantedAuthority(Permission.SENTITY_SAVE.name()))
                            || authorities.contains(new SimpleGrantedAuthority(Permission.SENTITY_INVALID.name()))
                            || authorities.contains(new SimpleGrantedAuthority(Permission.SENTITY_DELETE.name()))
            );
        }

        // 一覧を開く
        else if (Constants.OPERATION.LIST.equals(operation)) {
            return authorities.contains(new SimpleGrantedAuthority(Permission.SENTITY_LIST.name()));
        }

        // 参照
        else return Constants.OPERATION.VIEW.equals(operation);
    }

    /**
     * 許可されたOperationか
     *
     * @param operation 操作を表す定数
     */
    private void validate(String operation) {
        if (!allowedOperation.contains(operation)) {
            throw new IllegalArgumentException("Operation not allowed.");
        }
    }

}
