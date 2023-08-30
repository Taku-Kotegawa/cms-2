package jp.co.stnet.cms.equipment.presentation.controller.employee;

import jp.co.stnet.cms.base.domain.enums.Permission;
import jp.co.stnet.cms.base.domain.model.LoggedInUser;
import jp.co.stnet.cms.common.constant.Constants;
import jp.co.stnet.cms.equipment.domain.model.mbg.Employee;
import lombok.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

@Component
public class EmployeeAuthority {

    private final Set<String> allowedOperation = EmployeeConstant.ALLOWED_OPERATION;

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
     * @param Employee     エンティティ
     * @return true=操作する権限を持つ, 例外=権限を持たない場合
     * @throws AccessDeniedException    権限がない場合(@PostAuthorizeを用いて戻り値false時にスロー)
     * @throws IllegalArgumentException 不正なOperationが指定された場合
     * @throws NullPointerException     operation, loggedInUser がnullの場合
     */
    @PostAuthorize("returnObject == true")
    public Boolean hasAuthority(String operation, LoggedInUser loggedInUser, Employee Employee) {
        return hasAuthorityWOException(operation, loggedInUser, Employee);
    }

    /**
     * 権限の有無チェック
     *
     * @param operation    操作の種類(Constants.OPERATIONに登録された値)
     * @param loggedInUser ログインユーザ情報
     * @param Employee     エンティティ
     * @return true=操作する権限を持つ, false=権限を持たない
     * @throws IllegalArgumentException 不正なOperationが指定された場合
     * @throws NullPointerException     operation, loggedInUser がnullの場合
     */
    public Boolean hasAuthorityWOException(@NonNull String operation, @NonNull LoggedInUser loggedInUser, Employee Employee) {

        // 入力チェック
        validate(operation);

        // ログインユーザの権限を取得
        Collection<GrantedAuthority> authorities = loggedInUser.getAuthorities();
        if (authorities == null) {
            return false;
        }

        // 新規登録
        if (Constants.OPERATION.CREATE.equals(operation)) {
            return authorities.contains(new SimpleGrantedAuthority(Permission.EMPLOYEE_CREATE.name()));
        }

        // 編集画面を開く
        else if (Constants.OPERATION.UPDATE.equals(operation)) {
            return authorities.contains(new SimpleGrantedAuthority(Permission.EMPLOYEE_UPDATE.name()));
        }

        // 保存
        else if (Constants.OPERATION.SAVE.equals(operation)) {
            return authorities.contains(new SimpleGrantedAuthority(Permission.EMPLOYEE_SAVE.name()));
        }

        // 無効
        else if (Constants.OPERATION.INVALID.equals(operation)) {
            return authorities.contains(new SimpleGrantedAuthority(Permission.EMPLOYEE_INVALID.name()));
        }

        // 無効解除
        else if (Constants.OPERATION.VALID.equals(operation)) {
            return authorities.contains(new SimpleGrantedAuthority(Permission.EMPLOYEE_INVALID.name()));
        }

        // 削除
        else if (Constants.OPERATION.DELETE.equals(operation)) {
            return authorities.contains(new SimpleGrantedAuthority(Permission.EMPLOYEE_DELETE.name()));
        }

        // アップロード
        else if (Constants.OPERATION.UPLOAD.equals(operation)) {
            return authorities.contains(new SimpleGrantedAuthority(Permission.EMPLOYEE_UPLOAD.name()));
        }

        // ダウンロード
        else if (Constants.OPERATION.DOWNLOAD.equals(operation)) {
            return (
                    authorities.contains(new SimpleGrantedAuthority(Permission.EMPLOYEE_CREATE.name()))
                            || authorities.contains(new SimpleGrantedAuthority(Permission.EMPLOYEE_SAVE.name()))
                            || authorities.contains(new SimpleGrantedAuthority(Permission.EMPLOYEE_INVALID.name()))
                            || authorities.contains(new SimpleGrantedAuthority(Permission.EMPLOYEE_DELETE.name()))
            );
        }

        // 一覧を開く
        else if (Constants.OPERATION.LIST.equals(operation)) {
            return authorities.contains(new SimpleGrantedAuthority(Permission.EMPLOYEE_LIST.name()));
        }

        // 参照
        else return Constants.OPERATION.VIEW.equals(operation);
    }

    /**
     * 許可されたOperationか
     *
     * @param operation 操作を表す定数
     */
    void validate(String operation) {
        if (!allowedOperation.contains(operation)) {
            throw new IllegalArgumentException("Operation not allowed.");
        }
    }

}