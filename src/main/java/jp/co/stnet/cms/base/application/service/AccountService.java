package jp.co.stnet.cms.base.application.service;


import jp.co.stnet.cms.base.domain.model.AccountRole;
import jp.co.stnet.cms.base.domain.model.mbg.Account;
import jp.co.stnet.cms.base.domain.model.mbg.AccountExample;

import java.time.LocalDateTime;

/**
 * Accountサービス.
 */
public interface AccountService extends NodeIService<AccountRole, AccountExample, String> {

    /**
     * API KEY を削除する。
     *
     * @param username ユーザ名
     * @return Account
     */
    Account deleteApiKey(String username);

    /**
     * API KEY を保存する。
     *
     * @param username ユーザ名
     * @return Account
     */
    Account saveApiKey(String username);

    /**
     * 　API KEY で検索する。
     *
     * @param apiKey API KEY
     * @return Account
     */
    Account findByApiKey(String apiKey);

    /**
     * 最終ログイン日時を取得する。
     *
     * @param username ユーザ名
     * @return 最終ログイン日時
     */
    LocalDateTime getLastLoginDate(String username);

    /**
     * 新規アカウントを登録する。
     * 初期パスワードを設定し、返す。
     *
     * @param account アカウント・ロールエンティティ
     * @return 初期パスワード
     */
    String create(Account account);

    /**
     * ユーザアカウントの存在チェック
     *
     * @param username ユーザ名
     * @return true:存在する, false:存在しない
     */
    boolean exists(String username);

    /**
     * ユーザのロックを確認する。
     *
     * @param username ユーザ名
     * @return true:ロックしている, false:ロックしていない
     */
    boolean isLocked(String username);

    /**
     * パスワードが初期状態かどうか。
     *
     * @param username ユーザ名
     * @return true:初期状態, false:変更しれている
     */
    boolean isInitialPassword(String username);

    /**
     * パスワードの有効期限が切れているかどうか。
     *
     * @param username ユーザ名
     * @return true:有効期限が切れている, false:切れていない
     */
    boolean isCurrentPasswordExpired(String username);

    /**
     * パスワードを設定する。
     *
     * @param username    ユーザ名
     * @param rawPassword パスワード(ハッシュ化前)
     * @return true:パスワードが変更できた, false:変更できなかった
     */
    boolean updatePassword(String username, String rawPassword);

    /**
     * メールアドレスを設定する。
     *
     * @param username ユーザ名
     * @param email    メールアドレス
     * @return true:メールアドレスが変更できた, false:変更できなかった
     */
    boolean updateEmail(String username, String email);

    /**
     * キャッシュをクリアする。
     *
     * @param username
     */
    void clearPasswordValidationCache(String username);



}
