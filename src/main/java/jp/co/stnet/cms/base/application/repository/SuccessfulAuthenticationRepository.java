package jp.co.stnet.cms.base.application.repository;

import jp.co.stnet.cms.base.domain.model.mbg.SuccessfulAuthentication;
import jp.co.stnet.cms.base.domain.model.mbg.SuccessfulAuthenticationExample;
import jp.co.stnet.cms.base.domain.model.mbg.SuccessfulAuthenticationKey;
import jp.co.stnet.cms.base.infrastructure.mapper.MapperInterface;
import jp.co.stnet.cms.base.infrastructure.mapper.mbg.SuccessfulAuthenticationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Component
public class SuccessfulAuthenticationRepository extends AbstractRepository<SuccessfulAuthentication, SuccessfulAuthenticationExample, SuccessfulAuthenticationKey> {

    private final SuccessfulAuthenticationMapper mapper;

    @Override
    MapperInterface<SuccessfulAuthentication, SuccessfulAuthenticationExample, SuccessfulAuthenticationKey> mapper() {
        return mapper;
    }
}
