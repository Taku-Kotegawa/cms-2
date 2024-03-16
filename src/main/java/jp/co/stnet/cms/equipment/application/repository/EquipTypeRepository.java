package jp.co.stnet.cms.equipment.application.repository;

import jp.co.stnet.cms.base.application.repository.AbstractVersionRepository;
import jp.co.stnet.cms.base.application.repository.interfaces.VersionRepositoryInterface;
import jp.co.stnet.cms.base.infrastructure.mapper.VersionMapperInterface;
import jp.co.stnet.cms.equipment.domain.model.mbg.EquipType;
import jp.co.stnet.cms.equipment.domain.model.mbg.EquipTypeExample;
import jp.co.stnet.cms.equipment.infrastructure.mapper.mbg.EquipTypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Component
public class EquipTypeRepository extends AbstractVersionRepository<EquipType, EquipTypeExample, Long>
        implements VersionRepositoryInterface<EquipType, EquipTypeExample, Long> {

    @Autowired
    EquipTypeMapper mapper;

    @Override
    public VersionMapperInterface<EquipType, EquipTypeExample, Long> mapper() {
        return mapper;
    }

    @Override
    protected EquipTypeExample example() {
        return new EquipTypeExample();
    }

}