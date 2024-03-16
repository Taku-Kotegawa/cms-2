package jp.co.stnet.cms.equipment.presentation.controller.employee;

import jp.co.stnet.cms.base.domain.model.LoggedInUser;
import jp.co.stnet.cms.common.message.MessageKeys;
import jp.co.stnet.cms.common.util.OperationsUtil;

import jp.co.stnet.cms.equipment.application.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;

import static jp.co.stnet.cms.common.constant.Constants.*;
import static jp.co.stnet.cms.equipment.presentation.controller.employee.EmployeeConstant.BASE_PATH;

@RequiredArgsConstructor
@Controller
@RequestMapping(BASE_PATH)
public class EmployeeDeleteController {

    private final EmployeeService employeeService;
    private final EmployeeAuthority authority;
    private final OperationsUtil op = new OperationsUtil(BASE_PATH);

    /**
     * 削除
     */
    @GetMapping(value = "{id}/delete")
    public String delete(Model model, RedirectAttributes redirect, @AuthenticationPrincipal LoggedInUser loggedInUser,
                         @PathVariable("id") String id) {

        authority.hasAuthority(OPERATION.DELETE, loggedInUser);

        try {
            employeeService.delete(id);

        } catch (BusinessException e) {
            model.addAttribute(e.getResultMessages());
            return "redirect:" + op.getEditUrl(id);
        }

        ResultMessages messages = ResultMessages.info().add(MessageKeys.I_CM_FW_0003);
        redirect.addFlashAttribute(messages);

        return "redirect:" + op.getListUrl();
    }
}