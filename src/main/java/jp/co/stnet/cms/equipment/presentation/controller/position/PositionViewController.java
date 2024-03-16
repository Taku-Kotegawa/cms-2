package jp.co.stnet.cms.equipment.presentation.controller.position;

import jp.co.stnet.cms.base.domain.model.LoggedInUser;
import jp.co.stnet.cms.common.util.OperationsUtil;
import jp.co.stnet.cms.equipment.application.service.PositionService;
import jp.co.stnet.cms.equipment.domain.model.PositionBean;
import jp.co.stnet.cms.equipment.presentation.dto.PositionForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static jp.co.stnet.cms.common.constant.Constants.OPERATION;
import static jp.co.stnet.cms.equipment.presentation.controller.position.PositionConstant.*;

@RequiredArgsConstructor
@Controller
@RequestMapping(BASE_PATH)
public class PositionViewController {

    private final PositionHelper helper;
    private final PositionService positionService;
    private final PositionAuthority authority;
    private final OperationsUtil op = new OperationsUtil(BASE_PATH);

    @ModelAttribute
    PositionForm setUp() {
        return new PositionForm();
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
        var entity = positionService.findById(id);
        var positionBean = PositionBean.of(entity);

        model.addAttribute("pageTitle", PAGE_TITLE_VIEW);
        model.addAttribute("positionBean", positionBean);
        model.addAttribute("buttonState", helper.getButtonStateMap(OPERATION.VIEW, positionBean, null).asMap());
        model.addAttribute("fieldState", helper.getFiledStateMap(OPERATION.VIEW, positionBean, null).asMap());
        model.addAttribute("op", op);

        return TEMPLATE_FORM;
    }

}