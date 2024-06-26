package jp.co.stnet.cms.base.presentation.controller.admin.permission;

import jp.co.stnet.cms.base.application.service.PermissionService;
import jp.co.stnet.cms.base.domain.enums.Permission;
import jp.co.stnet.cms.base.domain.enums.Role;
import jp.co.stnet.cms.base.domain.model.LoggedInUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("admin/permission")
public class PermissionController {

    private final PermissionService permissionService;

    private final String BASE_PATH = "/admin/permission/";
    private final String JSP_LIST = "admin/permission/list";

    @ModelAttribute
    public PermissionForm setUp() {
        return new PermissionForm();
    }

    /**
     * 一覧画面の表示
     */
    @GetMapping(value = "list")
    public String list(Model model, PermissionForm form, @AuthenticationPrincipal LoggedInUser loggedInUser) {

        form.setPermissionRoles(permissionService.findAllMap());

        model.addAttribute(Permission.values());
        model.addAttribute(Role.values());

        return JSP_LIST;
    }

    /**
     * 保存
     */
    @PostMapping(value = "list")
    public String save(Model model, PermissionForm form, @AuthenticationPrincipal LoggedInUser loggedInUser) {

        permissionService.saveAll(form.getPermissionRoles());

        form.setPermissionRoles(permissionService.findAllMap());

//        form.setPermissionRoles(permissionService.findAllMap());

//        model.addAttribute(Permission.values());
//        model.addAttribute(Role.values());

//        return JSP_LIST;
        return "redirect:/admin/permission/list";
    }


}

