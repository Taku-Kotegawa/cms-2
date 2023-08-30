package jp.co.stnet.cms.equipment.application.repository;

import jp.co.stnet.cms.base.application.repository.AbstractVersionRepository;
import jp.co.stnet.cms.base.application.repository.interfaces.VersionRepositoryInterface;
import jp.co.stnet.cms.base.infrastructure.mapper.VersionMapperInterface;
import jp.co.stnet.cms.common.datatables.DataTablesInput;
import jp.co.stnet.cms.common.datatables.DataTablesUtil;
import jp.co.stnet.cms.common.util.BeanUtils;
import jp.co.stnet.cms.equipment.domain.model.mbg.Employee;
import jp.co.stnet.cms.equipment.domain.model.mbg.EmployeeExample;
import jp.co.stnet.cms.equipment.domain.model.mbg.VEmployee;
import jp.co.stnet.cms.equipment.domain.model.mbg.VEmployeeExample;
import jp.co.stnet.cms.equipment.infrastructure.mapper.EmployeeQueryMapper;
import jp.co.stnet.cms.equipment.infrastructure.mapper.mbg.EmployeeMapper;
import jp.co.stnet.cms.equipment.infrastructure.mapper.mbg.VEmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;

import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@Component
public class EmployeeRepository extends AbstractVersionRepository<Employee, EmployeeExample, String>
        implements VersionRepositoryInterface<Employee, EmployeeExample, String> {

    private final EmployeeMapper mapper;
    private final EmployeeQueryMapper queryMapper;
    private final VEmployeeMapper vEmployeeMapper;
    private final Map<String, String> fieldMap = BeanUtils.getFields(VEmployee.class, null);

    @Override
    public VersionMapperInterface<Employee, EmployeeExample, String> mapper() {
        return mapper;
    }

    @Override
    protected EmployeeExample example() {
        return new EmployeeExample();
    }

    public Page<VEmployee> findPageByInput(DataTablesInput input) {
        input.setWhereClause(DataTablesUtil.getWhereClause(input, fieldMap));
        var pageable = PageRequest.of(input.getStart() / input.getLength(), input.getLength());
        var entities = queryMapper.findPageByInput(input, pageable);
        var totalCount = queryMapper.countByInput(input);
        return new PageImpl<>(entities, pageable, totalCount);
    }

    public VEmployee findById2(String id) {
        Objects.requireNonNull(id);
        var example = new VEmployeeExample();
        example.or().andEmpIdEqualTo(id);
        var result = vEmployeeMapper.selectByExample(example);
        if (result.isEmpty()) {
            throw new ResourceNotFoundException("id = " + id);
        } else {
            return result.get(0);
        }
    }

}