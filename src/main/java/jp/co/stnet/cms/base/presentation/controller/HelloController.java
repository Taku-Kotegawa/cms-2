package jp.co.stnet.cms.base.presentation.controller;


import jp.co.stnet.cms.base.application.service.NewsService;
import jp.co.stnet.cms.common.component.RemoteIpHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@Controller
public class HelloController {

    private final NewsService newsService;
    private final RemoteIpHelper remoteIpHelper;

    /**
     * ログイン後のトップ画面を表示する
     *
     * @param model
     * @return
     */
    @GetMapping
    public String top(Model model) {

        log.info("ReportAddr: {}", remoteIpHelper.getClinetRemoteIp());

        var variableList = newsService.findOpenNews();

        //modelに属性を登録する
        model.addAttribute("variableList", variableList);

        return "top";
    }

}
