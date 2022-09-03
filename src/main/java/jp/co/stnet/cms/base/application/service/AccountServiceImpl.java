package jp.co.stnet.cms.base.application.service;


import jp.co.stnet.cms.base.application.repository.AccountRoleRepository;
import jp.co.stnet.cms.base.domain.model.AccountRole;
import jp.co.stnet.cms.base.domain.model.mbg.Account;
import jp.co.stnet.cms.base.domain.model.mbg.AccountExample;
import jp.co.stnet.cms.base.infrastructure.mapper.VersionMapperInterface;
import jp.co.stnet.cms.common.message.MessageKeys;
import lombok.RequiredArgsConstructor;
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
public class AccountServiceImpl extends AbstractNodeService<AccountRole, AccountExample, String> implements AccountService {

    private final AccountRoleRepository accountRoleRepository;

    @Override
    protected VersionMapperInterface<AccountRole, AccountExample, String> repository() {
        return accountRoleRepository;
    }

    @Override
    public AccountRole deleteApiKey(String username) {
        return updateApiKey(username, null);
    }

    @Override
    public AccountRole saveApiKey(String username) {
        return updateApiKey(username, generateApiKey());
    }

    /**
     * API-KEYを発番
     */
    private String generateApiKey() {
        return UUID.randomUUID().toString();
    }

    /**
     * API-KEYを更新
     *
     * @param username ユーザ名
     * @param apikey   API-KEY
     * @return
     */
    private AccountRole updateApiKey(String username, String apikey) {
        // 存在チェック
        findById(username);

        // API KEY を更新
        AccountRole accountRole = new AccountRole();
        accountRole.setUsername(username);
        accountRole.setApiKey(apikey);
        long count = accountRoleRepository.updateByPrimaryKeySelective(accountRole);

        // エラーハンドリング
        if (count == 0) {
            // todo 例外処理(API-KEYの更新に失敗しました。)
        }
        return accountRoleRepository.selectByPrimaryKey(username);
    }

    @Override
    public AccountRole findByApiKey(String apiKey) {
        AccountExample example = new AccountExample();
        example.or().andApiKeyEqualTo(apiKey);
        List<AccountRole> accounts = accountRoleRepository.selectByExample(example);

        if (accounts.isEmpty()) {
            throw new ResourceNotFoundException(ResultMessages.error().add(MessageKeys.E_SL_FW_5001, apiKey));
        }

        return accounts.get(0);
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
    public List<AccountRole> findAllById(Iterable<String> ids) {
        AccountExample example = new AccountExample();
        example.or().andUsernameIn((List<String>) ids);
        return accountRoleRepository.selectByExample(example);
    }
}
