package jp.co.stnet.cms.equipment.application.repository;

import jp.co.stnet.cms.base.application.repository.AbstractVersionRepository;
import jp.co.stnet.cms.base.application.repository.interfaces.VersionRepositoryInterface;
import jp.co.stnet.cms.base.infrastructure.mapper.VersionMapperInterface;
import jp.co.stnet.cms.equipment.domain.model.mbg.ManageEquip;
import jp.co.stnet.cms.equipment.domain.model.mbg.ManageEquipExample;
import jp.co.stnet.cms.equipment.infrastructure.mapper.mbg.ManageEquipMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Component
public class ManageEquipRepository extends AbstractVersionRepository<ManageEquip, ManageEquipExample, Long>
        implements VersionRepositoryInterface<ManageEquip, ManageEquipExample, Long> {

    @Autowired
    ManageEquipMapper mapper;

    @Override
    public VersionMapperInterface<ManageEquip, ManageEquipExample, Long> mapper() {
        return mapper;
    }

    @Override
    protected ManageEquipExample example() {
        return new ManageEquipExample();
    }

}