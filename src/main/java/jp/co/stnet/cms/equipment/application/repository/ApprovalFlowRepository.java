package jp.co.stnet.cms.equipment.application.repository;

import jp.co.stnet.cms.base.application.repository.AbstractVersionRepository;
import jp.co.stnet.cms.base.application.repository.interfaces.VersionRepositoryInterface;
import jp.co.stnet.cms.base.infrastructure.mapper.VersionMapperInterface;
import jp.co.stnet.cms.equipment.domain.model.mbg.ApprovalFlow;
import jp.co.stnet.cms.equipment.domain.model.mbg.ApprovalFlowExample;
import jp.co.stnet.cms.equipment.infrastructure.mapper.mbg.ApprovalFlowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Component
public class ApprovalFlowRepository extends AbstractVersionRepository<ApprovalFlow, ApprovalFlowExample, Long>
        implements VersionRepositoryInterface<ApprovalFlow, ApprovalFlowExample, Long> {

    @Autowired
    ApprovalFlowMapper mapper;

    @Override
    public VersionMapperInterface<ApprovalFlow, ApprovalFlowExample, Long> mapper() {
        return mapper;
    }

    @Override
    protected ApprovalFlowExample example() {
        return new ApprovalFlowExample();
    }

}
