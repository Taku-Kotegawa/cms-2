package jp.co.stnet.cms.equipment.application.service;


import jp.co.stnet.cms.base.application.service.NodeIService;
import jp.co.stnet.cms.common.datatables.DataTablesInput;
import jp.co.stnet.cms.equipment.domain.model.mbg.Employee;
import jp.co.stnet.cms.equipment.domain.model.mbg.EmployeeExample;
import jp.co.stnet.cms.equipment.domain.model.mbg.VEmployee;
import org.springframework.data.domain.Page;

/**
 * EmployeeService
 */
public interface EmployeeService extends NodeIService<Employee, EmployeeExample, String> {

    /**
     * 検索条件で検索する
     *
     * @param input 検索条件
     * @return 検索結果
     */
    Page<VEmployee> findPageByInput2(DataTablesInput input);

    public VEmployee findById2(String id);
}