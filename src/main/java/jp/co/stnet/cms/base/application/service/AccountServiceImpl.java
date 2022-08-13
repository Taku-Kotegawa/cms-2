package jp.co.stnet.cms.base.application.service;

import jp.co.stnet.cms.base.domain.model.Account;
import jp.co.stnet.cms.base.domain.model.AccountExample;
import jp.co.stnet.cms.base.infrastructure.mapper.AccountMapper;
import jp.co.stnet.cms.base.infrastructure.mapper.MapperInterface;
import jp.co.stnet.cms.common.message.MessageKeys;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;
import org.terasoluna.gfw.common.message.ResultMessages;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl extends AbstractNodeService<Account, AccountExample, String> implements AccountService {

    private AccountMapper accountMapper;

    @Override
    protected MapperInterface<Account, AccountExample, String> mapper() {
        return accountMapper;
    }

    @Override
    public Account deleteApiKey(String username) {
        return updateApiKey(username, null);
    }

    @Override
    public Account saveApiKey(String username) {
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
    private Account updateApiKey(String username, String apikey) {
        // 存在チェック
        findById(username);

        // API KEY を更新
        long count = accountMapper.updateByPrimaryKeySelective(
                Account.builder()
                        .username(username)
                        .apiKey(apikey)
                        .build());

        // エラーハンドリング
        if (count == 0) {
            // todo 例外処理(API-KEYの更新に失敗しました。)
        }
        return accountMapper.selectByPrimaryKey(username);
    }

    @Override
    public Account findByApiKey(String apiKey) {
        AccountExample example = new AccountExample();
        example.or().andApiKeyEqualTo(apiKey);
        List<Account> accounts = accountMapper.selectByExample(example);

        if (accounts.isEmpty()) {
            throw new ResourceNotFoundException(ResultMessages.error().add(MessageKeys.E_SL_FW_5001, apiKey));
        }

        return accounts.get(0);
    }

    @Override
    public List<Account> findAllById(Iterable<String> ids) {
        AccountExample example = new AccountExample();
        example.or().andUsernameIn((List<String>) ids);
        return accountMapper.selectByExample(example);
    }
}
