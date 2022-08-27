package jp.co.stnet.cms.base.application.service;


import jp.co.stnet.cms.base.domain.model.mbg.Account;
import jp.co.stnet.cms.base.domain.model.mbg.AccountExample;

/**
 * Accountサービス.
 */
public interface AccountService extends NodeIService<Account, AccountExample, String> {

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

}
