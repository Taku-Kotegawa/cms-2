package jp.co.stnet.cms.base.presentation.controller.admin.role;


import jp.co.stnet.cms.base.domain.enums.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/role")
public class RoleController {

    private final String BASE_PATH = "/admin/role/";
    private final String JSP_LIST = "admin/role/list";

    /**
     * 一覧画面の表示
     */
    @GetMapping(value = "list")
    public String list(Model model) {
        model.addAttribute(Role.values());
        return JSP_LIST;
    }


}
