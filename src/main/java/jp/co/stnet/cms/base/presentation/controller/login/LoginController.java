package jp.co.stnet.cms.base.presentation.controller.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("login")
public class LoginController {

    @RequestMapping
    public String index() {
        return "login/loginForm";
    }
}
