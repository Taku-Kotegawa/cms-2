package jp.co.stnet.cms.equipment.presentation.controller.organization;

import jp.co.stnet.cms.base.domain.model.LoggedInUser;
import jp.co.stnet.cms.common.util.OperationsUtil;
import jp.co.stnet.cms.equipment.application.service.OrganizationService;
import jp.co.stnet.cms.equipment.domain.model.OrganizationBean;
import jp.co.stnet.cms.equipment.presentation.dto.OrganizationForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static jp.co.stnet.cms.common.constant.Constants.*;
import static jp.co.stnet.cms.equipment.presentation.controller.organization.OrganizationConstant.BASE_PATH;
import static jp.co.stnet.cms.equipment.presentation.controller.organization.OrganizationConstant.TEMPLATE_FORM;

@RequiredArgsConstructor
@Controller
@RequestMapping(BASE_PATH)
public class OrganizationViewController {

    private final OrganizationHelper helper;
    private final OrganizationService organizationService;
    private final OrganizationAuthority authority;
    private final OperationsUtil op = new OperationsUtil(BASE_PATH);

    @ModelAttribute
    OrganizationForm setUp() {
        return new OrganizationForm();
    }

    /**
     * 参照画面の表示
     */
    @GetMapping(value = "{id}")
    public String view(Model model, @AuthenticationPrincipal LoggedInUser loggedInUser,
                       @PathVariable("id") Long id) {

        // 権限チェック
        authority.hasAuthority(OPERATION.VIEW, loggedInUser);

        // データ取得
        var entity = organizationService.findById(id);
        var organizationBean = OrganizationBean.of(entity);

        model.addAttribute("pageTitle", "部署 参照");
        model.addAttribute("organizationBean", organizationBean);
        model.addAttribute("buttonState", helper.getButtonStateMap(OPERATION.VIEW, organizationBean, null).asMap());
        model.addAttribute("fieldState", helper.getFiledStateMap(OPERATION.VIEW, organizationBean, null).asMap());
        model.addAttribute("op", op);

        return TEMPLATE_FORM;
    }

}