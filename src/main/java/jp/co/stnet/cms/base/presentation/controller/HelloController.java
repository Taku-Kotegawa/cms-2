package jp.co.stnet.cms.base.presentation.controller;


import jp.co.stnet.cms.base.application.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class HelloController {

    private final NewsService newsService;

    /**
     * ログイン後のトップ画面を表示する
     *
     * @param model
     * @return
     */
    @GetMapping
    public String top(Model model) {

        var variableList = newsService.findOpenNews();

        //modelに属性を登録する
        model.addAttribute("variableList", variableList);

        return "top";
    }

}
