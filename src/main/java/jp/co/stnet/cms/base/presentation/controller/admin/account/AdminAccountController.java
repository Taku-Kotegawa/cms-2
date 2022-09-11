package jp.co.stnet.cms.base.presentation.controller.admin.account;

import jp.co.stnet.cms.base.application.service.AccountService;
import jp.co.stnet.cms.base.domain.model.Account;
import jp.co.stnet.cms.base.domain.model.LoggedInUser;
import jp.co.stnet.cms.common.constant.Constants;
import jp.co.stnet.cms.common.util.OperationsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static jp.co.stnet.cms.base.presentation.controller.admin.account.AdminAccountConstant.BASE_PATH;
import static jp.co.stnet.cms.base.presentation.controller.admin.account.AdminAccountConstant.TEMPLATE_FORM;

@Controller
@RequestMapping(BASE_PATH)
public class AdminAccountController {

    @Autowired
    AdminAccountHelper helper;

    @Autowired
    AccountService accountService;

    @Autowired
    AdminAccountAuthority authority;

    @ModelAttribute
    AccountForm setUp() {
        return new AccountForm();
    }

    /**
     * 参照画面の表示
     */
    @GetMapping(value = "{username}")
    public String view(Model model, @AuthenticationPrincipal LoggedInUser loggedInUser,
                       @PathVariable("username") String username) {

        // 権限チェック
        authority.hasAuthority(Constants.OPERATION.VIEW, loggedInUser);

        // データ取得
        Account account = accountService.findById(username);
        model.addAttribute("account", account);

        // ロック状態確認
        model.addAttribute("isLocked", accountService.isLocked(username));

        model.addAttribute("buttonState", helper.getButtonStateMap(Constants.OPERATION.VIEW, account, null).asMap());
        model.addAttribute("fieldState", helper.getFiledStateMap(Constants.OPERATION.VIEW, account, null).asMap());
        model.addAttribute("op", new OperationsUtil(BASE_PATH));

        return TEMPLATE_FORM;
    }

}
