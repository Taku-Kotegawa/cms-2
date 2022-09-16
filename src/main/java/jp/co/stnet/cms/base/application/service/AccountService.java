package jp.co.stnet.cms.base.application.service;


import jp.co.stnet.cms.base.domain.model.Account;
import jp.co.stnet.cms.base.domain.model.mbg.TAccountExample;

import java.util.List;

/**
 * Accountサービス.
 */
public interface AccountService extends NodeIService<Account, TAccountExample, String> {

    /**
     * API KEY を発行する。
     *
     * @return API KEY
     */
    String generateApiKey();

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
     * キーで検索(複数)
     *
     * @param ids キーのリスト
     * @return アカウントのリスト
     */
    List<Account> findAllById(List<String> ids);

}
