package jp.co.stnet.cms.equipment.application.service;

import jp.co.stnet.cms.base.application.repository.interfaces.VersionRepositoryInterface;
import jp.co.stnet.cms.base.application.service.AbstractNodeService;
import jp.co.stnet.cms.common.datatables.DataTablesInput;
import jp.co.stnet.cms.equipment.application.repository.PositionRepository;
import jp.co.stnet.cms.equipment.domain.model.mbg.Position;
import jp.co.stnet.cms.equipment.domain.model.mbg.PositionExample;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class PositionServiceImpl extends AbstractNodeService<Position, PositionExample, Long> implements PositionService {

    private final PositionRepository repository;

    @Override
    protected VersionRepositoryInterface<Position, PositionExample, Long> repository() {
        return repository;
    }

    @Override
    public Page<Position> findPageByInput(DataTablesInput input) {
        return repository.findPageByInput(input);
    }
}