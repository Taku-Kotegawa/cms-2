package jp.co.stnet.cms.base.application.repository;

import jp.co.stnet.cms.base.domain.model.mbg.FailedPasswordReissue;
import jp.co.stnet.cms.base.domain.model.mbg.FailedPasswordReissueExample;
import jp.co.stnet.cms.base.domain.model.mbg.FailedPasswordReissueKey;
import jp.co.stnet.cms.base.infrastructure.mapper.MapperInterface;
import jp.co.stnet.cms.base.infrastructure.mapper.mbg.FailedPasswordReissueMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Transactional
@Component
public class FailedPasswordReissueRepository extends AbstractRepository<FailedPasswordReissue, FailedPasswordReissueExample, FailedPasswordReissueKey> {

    private final FailedPasswordReissueMapper mapper;

    @Override
    MapperInterface<FailedPasswordReissue, FailedPasswordReissueExample, FailedPasswordReissueKey> mapper() {
        return mapper;
    }

    @Override
    FailedPasswordReissueExample example() {
        return new FailedPasswordReissueExample();
    }

    public void deleteByToken(String token) {
        var example = new FailedPasswordReissueExample();
        example.or().andTokenEqualTo(token);
        mapper.deleteByExample(example);
    }

    public void deleteByAttemptDateLessThan(LocalDateTime date) {
        var example = new FailedPasswordReissueExample();
        example.or().andAttemptDateLessThan(date);
        mapper.deleteByExample(example);
    }

    public long countByToken(String token) {
        var example = new FailedPasswordReissueExample();
        example.or().andTokenEqualTo(token);
        return mapper.countByExample(example);
    }

}
