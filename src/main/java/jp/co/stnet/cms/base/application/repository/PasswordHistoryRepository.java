package jp.co.stnet.cms.base.application.repository;

import jp.co.stnet.cms.base.domain.model.mbg.PasswordHistory;
import jp.co.stnet.cms.base.domain.model.mbg.PasswordHistoryExample;
import jp.co.stnet.cms.base.domain.model.mbg.PasswordHistoryKey;
import jp.co.stnet.cms.base.infrastructure.mapper.MapperInterface;
import jp.co.stnet.cms.base.infrastructure.mapper.mbg.PasswordHistoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Component
public class PasswordHistoryRepository extends AbstractRepository<PasswordHistory, PasswordHistoryExample, PasswordHistoryKey> {

    private final PasswordHistoryMapper mapper;

    @Override
    MapperInterface<PasswordHistory, PasswordHistoryExample, PasswordHistoryKey> mapper() {
        return mapper;
    }
}
