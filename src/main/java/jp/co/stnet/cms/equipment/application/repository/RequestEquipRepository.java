package jp.co.stnet.cms.equipment.application.repository;

import jp.co.stnet.cms.base.application.repository.AbstractVersionRepository;
import jp.co.stnet.cms.base.application.repository.interfaces.VersionRepositoryInterface;
import jp.co.stnet.cms.base.infrastructure.mapper.VersionMapperInterface;
import jp.co.stnet.cms.equipment.domain.model.mbg.RequestEquip;
import jp.co.stnet.cms.equipment.domain.model.mbg.RequestEquipExample;
import jp.co.stnet.cms.equipment.infrastructure.mapper.mbg.RequestEquipMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Component
public class RequestEquipRepository extends AbstractVersionRepository<RequestEquip, RequestEquipExample, Long>
        implements VersionRepositoryInterface<RequestEquip, RequestEquipExample, Long> {

    @Autowired
    RequestEquipMapper mapper;

    @Override
    public VersionMapperInterface<RequestEquip, RequestEquipExample, Long> mapper() {
        return mapper;
    }

    @Override
    protected RequestEquipExample example() {
        return new RequestEquipExample();
    }

}