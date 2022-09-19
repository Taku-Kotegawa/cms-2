package jp.co.stnet.cms.base.application.service;


import jp.co.stnet.cms.base.application.repository.AccountRepository;
import jp.co.stnet.cms.base.application.repository.interfaces.VersionRepositoryInterface;
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

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Service
public class AccountServiceImpl extends AbstractNodeService<Account, TAccountExample, String> implements AccountService {

    private final AccountRepository repository;

    @Override
    protected VersionRepositoryInterface<Account, TAccountExample, String> repository() {
        return repository;
    }

    @Override
    public String generateApiKey() {
        return UUID.randomUUID().toString();
    }

    @Override
    public Account deleteApiKey(String username) {
        findById(username); // 存在チェック
        var entity = repository.findById(username);
        if (entity.isPresent()) {
            entity.get().setApiKey(null);
            return repository.save(entity.get());
        }
        return null;
    }

    @Override
    public Account saveApiKey(String username) {
        return updateApiKey(username, generateApiKey());
    }

    /**
     * API-KEYを更新
     *
     * @param username ユーザ名
     * @param apikey   API-KEY
     * @return
     */
    private Account updateApiKey(String username, String apikey) {
        Objects.requireNonNull(apikey);
        findById(username);
        long count = repository.updateApiKey(username, apikey);
        if (count == 0) {
            // todo 例外処理(API-KEYの更新に失敗しました。)
        }
        return repository.getOne(username);
    }

    @Override
    public Account findByApiKey(String apiKey) {
        var example = new TAccountExample();
        example.or().andApiKeyEqualTo(apiKey);
        List<Account> entities = repository.findAllByExample(example);
        if (entities.isEmpty()) {
            throw new ResourceNotFoundException(ResultMessages.error().add(MessageKeys.E_SL_FW_5001, apiKey));
        }

        return entities.get(0);
    }

    @Override
    public List<Account> findAllById(List<String> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public Page<Account> findPageByInput(DataTablesInput input) {
        return repository.findPageByInput(input);
    }
}
