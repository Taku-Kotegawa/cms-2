package jp.co.stnet.cms.equipment.application.repository;

import jp.co.stnet.cms.base.application.repository.AbstractVersionRepository;
import jp.co.stnet.cms.base.application.repository.interfaces.VersionRepositoryInterface;
import jp.co.stnet.cms.base.infrastructure.mapper.VersionMapperInterface;
import jp.co.stnet.cms.common.datatables.DataTablesInput;
import jp.co.stnet.cms.common.datatables.DataTablesUtil;
import jp.co.stnet.cms.common.util.BeanUtils;
import jp.co.stnet.cms.equipment.domain.model.mbg.Organization;
import jp.co.stnet.cms.equipment.domain.model.mbg.OrganizationExample;
import jp.co.stnet.cms.equipment.domain.model.mbg.VEmployee;
import jp.co.stnet.cms.equipment.infrastructure.mapper.OrganizationQueryMapper;
import jp.co.stnet.cms.equipment.infrastructure.mapper.mbg.OrganizationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@RequiredArgsConstructor
@Transactional
@Component
public class OrganizationRepository extends AbstractVersionRepository<Organization, OrganizationExample, Long>
        implements VersionRepositoryInterface<Organization, OrganizationExample, Long> {

    private final OrganizationMapper mapper;
    private final OrganizationQueryMapper queryMapper;
    private final Map<String, String> fieldMap = BeanUtils.getFields(Organization.class, null);

    @Override
    public VersionMapperInterface<Organization, OrganizationExample, Long> mapper() {
        return mapper;
    }

    @Override
    protected OrganizationExample example() {
        return new OrganizationExample();
    }

    public Page<Organization> findPageByInput(DataTablesInput input) {
        input.setWhereClause(DataTablesUtil.getWhereClause(input, fieldMap));
        var pageable = PageRequest.of(input.getStart() / input.getLength(), input.getLength());
        var entities = queryMapper.findPageByInput(input, pageable);
        var totalCount = queryMapper.countByInput(input);
        return new PageImpl<>(entities, pageable, totalCount);
    }
}