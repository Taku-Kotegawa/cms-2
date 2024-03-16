package jp.co.stnet.cms.equipment.application.service;

import jp.co.stnet.cms.base.application.repository.interfaces.VersionRepositoryInterface;
import jp.co.stnet.cms.base.application.service.AbstractNodeService;
import jp.co.stnet.cms.common.datatables.DataTablesInput;
import jp.co.stnet.cms.equipment.application.repository.EmployeeRepository;
import jp.co.stnet.cms.equipment.domain.model.mbg.Employee;
import jp.co.stnet.cms.equipment.domain.model.mbg.EmployeeExample;
import jp.co.stnet.cms.equipment.domain.model.mbg.VEmployee;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class EmployeeServiceImpl extends AbstractNodeService<Employee, EmployeeExample, String> implements EmployeeService {

    private final EmployeeRepository repository;

    @Override
    protected VersionRepositoryInterface<Employee, EmployeeExample, String> repository() {
        return repository;
    }

    @Override
    @Deprecated
    public Page<Employee> findPageByInput(DataTablesInput input) {
        return null;
    }

    public VEmployee findById2(String id) {
        return repository.findById2(id);
    };

    public Page<VEmployee> findPageByInput2(DataTablesInput input) {
        return repository.findPageByInput(input);
    }

}