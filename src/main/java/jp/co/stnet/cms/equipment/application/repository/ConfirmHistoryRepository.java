package jp.co.stnet.cms.equipment.application.repository;

import jp.co.stnet.cms.base.application.repository.AbstractVersionRepository;
import jp.co.stnet.cms.base.application.repository.interfaces.VersionRepositoryInterface;
import jp.co.stnet.cms.base.infrastructure.mapper.VersionMapperInterface;
import jp.co.stnet.cms.equipment.domain.model.mbg.ConfirmHistory;
import jp.co.stnet.cms.equipment.domain.model.mbg.ConfirmHistoryExample;
import jp.co.stnet.cms.equipment.infrastructure.mapper.mbg.ConfirmHistoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Component
public class ConfirmHistoryRepository extends AbstractVersionRepository<ConfirmHistory, ConfirmHistoryExample, Long>
        implements VersionRepositoryInterface<ConfirmHistory, ConfirmHistoryExample, Long> {

    @Autowired
    ConfirmHistoryMapper mapper;

    @Override
    public VersionMapperInterface<ConfirmHistory, ConfirmHistoryExample, Long> mapper() {
        return mapper;
    }

    @Override
    protected ConfirmHistoryExample example() {
        return new ConfirmHistoryExample();
    }

}