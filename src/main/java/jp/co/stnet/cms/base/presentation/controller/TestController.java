package jp.co.stnet.cms.base.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @RequestMapping
    @ResponseBody
    public String test() {
        return "test";
    }

    @RequestMapping("api")
    @ResponseBody
    public String api() {
        return "api";
    }


}
