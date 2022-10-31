package jp.co.stnet.cms.base.application.repository;


import jp.co.stnet.cms.base.domain.model.mbg.FailedEmailChangeRequest;
import jp.co.stnet.cms.base.domain.model.mbg.FailedEmailChangeRequestExample;
import jp.co.stnet.cms.base.domain.model.mbg.FailedEmailChangeRequestKey;
import jp.co.stnet.cms.base.infrastructure.mapper.MapperInterface;
import jp.co.stnet.cms.base.infrastructure.mapper.mbg.FailedEmailChangeRequestMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Component
public class FailedEmailChangeRequestRepository extends AbstractRepository<FailedEmailChangeRequest, FailedEmailChangeRequestExample, FailedEmailChangeRequestKey> {

    private final FailedEmailChangeRequestMapper mapper;

    @Override
    MapperInterface<FailedEmailChangeRequest, FailedEmailChangeRequestExample, FailedEmailChangeRequestKey> mapper() {
        return mapper;
    }

    @Override
    FailedEmailChangeRequestExample example() {
        return new FailedEmailChangeRequestExample();
    }

    public long countByToken(String token) {
        var example = new FailedEmailChangeRequestExample();
        example.or().andTokenEqualTo(token);
        return mapper.countByExample(example);
    }

    public long deleteByToken(String token) {
        var example = new FailedEmailChangeRequestExample();
        example.or().andTokenEqualTo(token);
        return mapper.deleteByExample(example);
    }

    public long deleteByAttemptDateLessThan(LocalDateTime expireDate) {
        // DELETE FROM failedPasswordReissue WHERE expiry_date < #{date}
        var example = new FailedEmailChangeRequestExample();
        example.or().andAttemptDateLessThan(expireDate);
        return mapper.deleteByExample(example);
    }

}
