package jp.co.stnet.cms.base.application.repository;

import jp.co.stnet.cms.base.domain.model.mbg.FailedAuthentication;
import jp.co.stnet.cms.base.domain.model.mbg.FailedAuthenticationExample;
import jp.co.stnet.cms.base.domain.model.mbg.FailedAuthenticationKey;
import jp.co.stnet.cms.base.infrastructure.mapper.MapperInterface;
import jp.co.stnet.cms.base.infrastructure.mapper.mbg.FailedAuthenticationMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Transactional
@Component
public class FailedAuthenticationRepository extends AbstractRepository<FailedAuthentication, FailedAuthenticationExample, FailedAuthenticationKey> {

    private final FailedAuthenticationMapper mapper;

    @Override
    MapperInterface<FailedAuthentication, FailedAuthenticationExample, FailedAuthenticationKey> mapper() {
        return mapper;
    }
}
