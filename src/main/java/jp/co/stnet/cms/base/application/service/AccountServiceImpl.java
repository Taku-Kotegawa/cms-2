package jp.co.stnet.cms.base.application.service;


import jp.co.stnet.cms.base.application.repository.AccountTestRepository;
import jp.co.stnet.cms.base.domain.model.Account;
import jp.co.stnet.cms.base.domain.model.mbg.TAccountExample;
import jp.co.stnet.cms.common.datatables.DataTablesInput;
import jp.co.stnet.cms.common.message.MessageKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;
import org.terasoluna.gfw.common.message.ResultMessages;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountTestRepository accountTestRepository;


    @Override
    public Account deleteApiKey(String username) {
        return updateApiKey(username, null);
    }

    @Override
    public Account saveApiKey(String username) {
        return updateApiKey(username, generateApiKey());
    }

    @Override
    public String generateApiKey() {
        return UUID.randomUUID().toString();
    }

    /**
     * API-KEYを更新
     *
     * @param username ユーザ名
     * @param apikey   API-KEY
     * @return
     */
    private Account updateApiKey(String username, String apikey) {
        // 存在チェック
        findById(username);

        // API KEY を更新
        Account accountAndRoles = new Account();
        accountAndRoles.setUsername(username);
        accountAndRoles.setApiKey(apikey);
        long count = accountTestRepository.updateByPrimaryKeySelective(accountAndRoles);

        // エラーハンドリング
        if (count == 0) {
            // todo 例外処理(API-KEYの更新に失敗しました。)
        }
        return accountTestRepository.selectByPrimaryKey(username);
    }

    @Override
    public Account findByApiKey(String apiKey) {
        var example = new TAccountExample();
        example.or().andApiKeyEqualTo(apiKey);
        List<Account> accountAndRoles = accountTestRepository.selectByExample(example);

        if (accountAndRoles.isEmpty()) {
            throw new ResourceNotFoundException(ResultMessages.error().add(MessageKeys.E_SL_FW_5001, apiKey));
        }

        return accountAndRoles.get(0);
    }

    @Override
    public LocalDateTime getLastLoginDate(String username) {
        return null;
    }

    @Override
    public String create(Account account) {
        return null;
    }

    @Override
    public boolean exists(String username) {
        return false;
    }

    @Override
    public boolean isLocked(String username) {
        return false;
    }

    @Override
    public boolean isInitialPassword(String username) {
        return false;
    }

    @Override
    public boolean isCurrentPasswordExpired(String username) {
        return false;
    }

    @Override
    public boolean updatePassword(String username, String rawPassword) {
        return false;
    }

    @Override
    public boolean updateEmail(String username, String email) {
        return false;
    }

    @Override
    public void clearPasswordValidationCache(String username) {

    }

    @Override
    public Page<Account> findPageByInput(DataTablesInput input) {
        return null;
    }

    @Override
    public Account findById(String id) {
        return null;
    }

    @Override
    public List<Account> findAllByExample(TAccountExample example) {
        return null;
    }

    @Override
    public Account save(Account entity) {
        return null;
    }


    @Override
    public Account invalid(String id) {
        return null;
    }

    @Override
    public Account valid(String id) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public boolean equalsEntity(Account entity, Account other) {
        return false;
    }


    @Override
    public List<Account> findAllById(List<String> ids) {
        var example = new TAccountExample();
        example.or().andUsernameIn((List<String>) ids);
        return accountTestRepository.selectByExample(example);
    }
}
