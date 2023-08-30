package jp.co.stnet.cms.equipment.application.service;

import jp.co.stnet.cms.base.application.repository.interfaces.VersionRepositoryInterface;
import jp.co.stnet.cms.base.application.service.AbstractNodeService;
import jp.co.stnet.cms.common.datatables.DataTablesInput;
import jp.co.stnet.cms.equipment.application.repository.EquipTypeRepository;
import jp.co.stnet.cms.equipment.domain.model.mbg.EquipType;
import jp.co.stnet.cms.equipment.domain.model.mbg.EquipTypeExample;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class EquipTypeServiceImpl extends AbstractNodeService<EquipType, EquipTypeExample, Long> implements EquipTypeService {

    private final EquipTypeRepository repository;

    @Override
    protected VersionRepositoryInterface<EquipType, EquipTypeExample, Long> repository() {
        return repository;
    }

    @Override
    public Page<EquipType> findPageByInput(DataTablesInput input) {
        return null; // あとから実装
    }
}