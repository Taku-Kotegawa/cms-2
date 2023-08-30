package jp.co.stnet.cms.equipment.presentation.controller.employee;

import jp.co.stnet.cms.base.domain.model.LoggedInUser;
import jp.co.stnet.cms.common.constant.Constants;
import jp.co.stnet.cms.common.util.OperationsUtil;

import jp.co.stnet.cms.equipment.application.service.EmployeeService;
import jp.co.stnet.cms.equipment.domain.model.EmployeeBean;
import jp.co.stnet.cms.equipment.presentation.dto.EmployeeForm;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static jp.co.stnet.cms.equipment.presentation.controller.employee.EmployeeConstant.*;

@RequiredArgsConstructor
@Controller
@RequestMapping(BASE_PATH)
public class EmployeeViewController {

    private final EmployeeHelper helper;
    private final EmployeeService employeeService;
    private final EmployeeAuthority authority;
    private final OperationsUtil op = new OperationsUtil(BASE_PATH);

    @ModelAttribute
    EmployeeForm setUp() {
        return new EmployeeForm();
    }

    /**
     * 参照画面の表示
     */
    @GetMapping(value = "{id}")
    public String view(Model model, @AuthenticationPrincipal LoggedInUser loggedInUser,
                       @PathVariable("id") String id) {

        // 権限チェック
        authority.hasAuthority(Constants.OPERATION.VIEW, loggedInUser);

        // データ取得
        var entity = employeeService.findById2(id);
        var employeeBean = EmployeeBean.of(entity);

        model.addAttribute("pageTitle", "従業員 " + employeeBean.getEmpId() + " : " + employeeBean.getEmpName());
        model.addAttribute("employeeBean", employeeBean);
        model.addAttribute("buttonState", helper.getButtonStateMap(Constants.OPERATION.VIEW, employeeBean, null).asMap());
        model.addAttribute("fieldState", helper.getFiledStateMap(Constants.OPERATION.VIEW, employeeBean, null).asMap());
        model.addAttribute("op", op);

        return TEMPLATE_FORM;
    }

}