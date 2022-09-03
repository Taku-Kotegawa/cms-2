package jp.co.stnet.cms.base.application.repository;

import jp.co.stnet.cms.base.domain.model.mbg.PasswordReissueInfo;
import jp.co.stnet.cms.base.domain.model.mbg.PasswordReissueInfoExample;
import jp.co.stnet.cms.base.infrastructure.mapper.MapperInterface;
import jp.co.stnet.cms.base.infrastructure.mapper.mbg.PasswordReissueInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Component
public class PasswordReissueInfoRepository extends AbstractRepository<PasswordReissueInfo, PasswordReissueInfoExample, String> {

    private final PasswordReissueInfoMapper mapper;

    @Override
    MapperInterface<PasswordReissueInfo, PasswordReissueInfoExample, String> mapper() {
        return mapper;
    }
}
