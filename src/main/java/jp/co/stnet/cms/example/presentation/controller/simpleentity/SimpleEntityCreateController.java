package jp.co.stnet.cms.example.presentation.controller.simpleentity;


import jp.co.stnet.cms.base.domain.enums.Status;
import jp.co.stnet.cms.base.domain.model.LoggedInUser;
import jp.co.stnet.cms.common.constant.Constants;
import jp.co.stnet.cms.common.message.MessageKeys;
import jp.co.stnet.cms.common.util.OperationsUtil;
import jp.co.stnet.cms.example.application.service.SimpleEntityService;
import jp.co.stnet.cms.example.domain.model.SimpleEntity;
import jp.co.stnet.cms.example.presentation.request.SimpleEntityForm;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;

import javax.validation.groups.Default;

import static jp.co.stnet.cms.example.presentation.controller.simpleentity.SimpleEntityConstant.BASE_PATH;
import static jp.co.stnet.cms.example.presentation.controller.simpleentity.SimpleEntityConstant.TEMPLATE_FORM;

@RequiredArgsConstructor
@Controller
@RequestMapping(BASE_PATH)
@TransactionTokenCheck(BASE_PATH)
public class SimpleEntityCreateController {

    private final SimpleEntityHelper helper;
    private final ModelMapper modelMapper;
    private final SimpleEntityAuthority authority;
    private final SimpleEntityService simpleEntityService;

    @ModelAttribute
    SimpleEntityForm setUp() {
        // 初期値設定
        var form = SimpleEntityForm.builder()
                .status(Status.VALID.getCodeValue())
                .build();
        helper.addLastOneEmptyLine(form);
        return form;
    }

    /**
     * 新規作成画面を開く
     */
    @GetMapping(value = "create", params = "form")
    @TransactionTokenCheck(type = TransactionTokenType.BEGIN)
    public String createForm(SimpleEntityForm form,
                             Model model,
                             @AuthenticationPrincipal LoggedInUser loggedInUser,
                             @RequestParam(value = "copy", required = false) Long copy) {

        authority.hasAuthority(Constants.OPERATION.CREATE, loggedInUser);

        if (copy != null) {
            var source = simpleEntityService.findById(copy);
            modelMapper.map(source, form);
        }

        helper.addLastOneEmptyLine(form);

        model.addAttribute("buttonState", helper.getButtonStateMap(Constants.OPERATION.CREATE, null, form).asMap());
        model.addAttribute("fieldState", helper.getFiledStateMap(Constants.OPERATION.CREATE, null, form).asMap());
        model.addAttribute("op", new OperationsUtil(BASE_PATH));

        return TEMPLATE_FORM;
    }

    /**
     * 新規登録
     */
    @PostMapping(value = "create")
    @TransactionTokenCheck
    public String create(@Validated({SimpleEntityForm.Create.class, Default.class}) SimpleEntityForm form,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirect,
                         @AuthenticationPrincipal LoggedInUser loggedInUser) {

        authority.hasAuthority(Constants.OPERATION.CREATE, loggedInUser);

        if (bindingResult.hasErrors()) {
            return createForm(form, model, loggedInUser, null);
        }

        var entity = modelMapper.map(form, SimpleEntity.class);

        try {
            simpleEntityService.save(entity);
        } catch (BusinessException e) {
            model.addAttribute(e.getResultMessages());
            return createForm(form, model, loggedInUser, null);
        }

        ResultMessages messages = ResultMessages.info().add(MessageKeys.I_CM_FW_0001);
        redirect.addFlashAttribute(messages);

        OperationsUtil op = new OperationsUtil(BASE_PATH);
        return "redirect:" + op.getEditUrl(entity.getId().toString());
    }
}
