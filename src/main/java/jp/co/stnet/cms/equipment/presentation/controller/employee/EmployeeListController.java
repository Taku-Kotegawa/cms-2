package jp.co.stnet.cms.equipment.presentation.controller.employee;

import jp.co.stnet.cms.base.domain.model.LoggedInUser;
import jp.co.stnet.cms.common.constant.Constants;
import jp.co.stnet.cms.common.datatables.DataTablesInput;
import jp.co.stnet.cms.common.datatables.DataTablesOutput;
import jp.co.stnet.cms.equipment.application.service.EmployeeService;
import jp.co.stnet.cms.equipment.domain.model.EmployeeBean;
import jp.co.stnet.cms.equipment.domain.model.mbg.VEmployee;
import jp.co.stnet.cms.equipment.presentation.dto.EmployeeListBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

import static jp.co.stnet.cms.equipment.presentation.controller.employee.EmployeeConstant.BASE_PATH;
import static jp.co.stnet.cms.equipment.presentation.controller.employee.EmployeeConstant.TEMPLATE_LIST;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping(BASE_PATH)
public class EmployeeListController {

    private final EmployeeService employeeService;
    private final EmployeeAuthority authority;

    /**
     * 一覧画面の表示
     */
    @GetMapping(value = "list")
    public String list(Model model, @AuthenticationPrincipal LoggedInUser loggedInUser) {
        authority.hasAuthority(Constants.OPERATION.LIST, loggedInUser);
        return TEMPLATE_LIST;
    }

    /**
     * DataTables用のJSONの作成
     *
     * @param input DataTablesから要求
     * @return JSON
     */
    @ResponseBody
    @GetMapping(value = "/list/json")
    public DataTablesOutput<EmployeeListBean> listJson(@Validated DataTablesInput input,
                                                       @AuthenticationPrincipal LoggedInUser loggedInUser) {

        authority.hasAuthority(Constants.OPERATION.LIST, loggedInUser);

        List<EmployeeListBean> list = new ArrayList<>();
        Page<VEmployee> page = employeeService.findPageByInput2(input);

        for (VEmployee vEmployee : page.getContent()) {
            list.add(EmployeeListBean.of(EmployeeBean.of(vEmployee)));
        }

        DataTablesOutput<EmployeeListBean> output = new DataTablesOutput<>();
        output.setData(list);
        output.setDraw(input.getDraw());
        output.setRecordsTotal(0);
        output.setRecordsFiltered(page.getTotalElements());

        return output;
    }

}
