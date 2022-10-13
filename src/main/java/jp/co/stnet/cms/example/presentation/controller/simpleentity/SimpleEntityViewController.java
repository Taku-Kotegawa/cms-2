package jp.co.stnet.cms.example.presentation.controller.simpleentity;

import jp.co.stnet.cms.base.domain.model.Account;
import jp.co.stnet.cms.base.domain.model.LoggedInUser;
import jp.co.stnet.cms.base.presentation.controller.admin.account.AccountForm;
import jp.co.stnet.cms.common.constant.Constants;
import jp.co.stnet.cms.common.util.OperationsUtil;
import jp.co.stnet.cms.example.application.service.SimpleEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static jp.co.stnet.cms.example.presentation.controller.simpleentity.SimpleEntityConstant.BASE_PATH;
import static jp.co.stnet.cms.example.presentation.controller.simpleentity.SimpleEntityConstant.TEMPLATE_FORM;


@RequiredArgsConstructor
@Controller
@RequestMapping(BASE_PATH)
public class SimpleEntityViewController {

    private final SimpleEntityHelper helper;
    private final SimpleEntityService simpleEntityService;
    private final SimpleEntityAuthority authority;

    @ModelAttribute
    SimpleEntityForm setUp() {
        return new SimpleEntityForm();
    }

    /**
     * 参照画面の表示
     */
    @GetMapping(value = "{id}")
    public String view(Model model, @AuthenticationPrincipal LoggedInUser loggedInUser,
                       @PathVariable("id") Long id) {

        // 権限チェック
        authority.hasAuthority(Constants.OPERATION.VIEW, loggedInUser);

        // データ取得
        var simpleEntity = simpleEntityService.findById(id);
        model.addAttribute("simpleEntity", helper.copyFrom(simpleEntity));
        model.addAttribute("buttonState", helper.getButtonStateMap(Constants.OPERATION.VIEW, simpleEntity, null).asMap());
        model.addAttribute("fieldState", helper.getFiledStateMap(Constants.OPERATION.VIEW, simpleEntity, null).asMap());
        model.addAttribute("op", new OperationsUtil(BASE_PATH));

        return TEMPLATE_FORM;
    }

}
