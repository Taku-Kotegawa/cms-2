package jp.co.stnet.cms.base.application.repository;


import jp.co.stnet.cms.base.domain.model.mbg.Account;
import jp.co.stnet.cms.base.domain.model.mbg.AccountExample;
import jp.co.stnet.cms.base.infrastructure.mapper.VersionMapperInterface;
import jp.co.stnet.cms.base.infrastructure.mapper.mbg.AccountMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Transactional
@Component
public class AccountRepository extends AbstractVersionRepository<Account, AccountExample, String> {

    private final AccountMapper accountMapper;

    @Override
    protected VersionMapperInterface<Account, AccountExample, String> mapper() {
        return accountMapper;
    }
}
