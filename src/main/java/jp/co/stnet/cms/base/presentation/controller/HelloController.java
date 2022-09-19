package jp.co.stnet.cms.base.presentation.controller;


import jp.co.stnet.cms.base.application.service.NewsService;
import jp.co.stnet.cms.base.domain.model.mbg.Variable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HelloController {

    @Autowired
    NewsService newsService;

    /**
     * ログイン後のトップ画面を表示する
     *
     * @param model
     * @return
     */
    @GetMapping
    public String top(Model model) {

        List<Variable> variableList = newsService.findOpenNews();

        //modelに属性を登録する
        model.addAttribute("variableList",variableList);

        return "top";
    }

}