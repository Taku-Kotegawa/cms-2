package jp.co.stnet.cms.equipment.presentation.controller.position;

import jp.co.stnet.cms.base.domain.enums.Status;
import jp.co.stnet.cms.base.domain.model.LoggedInUser;
import jp.co.stnet.cms.common.message.MessageKeys;
import jp.co.stnet.cms.common.util.OperationsUtil;
import jp.co.stnet.cms.equipment.application.service.PositionService;
import jp.co.stnet.cms.equipment.domain.model.mbg.Position;
import jp.co.stnet.cms.equipment.presentation.dto.PositionForm;
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

import jakarta.validation.groups.Default;

import static jp.co.stnet.cms.common.constant.Constants.OPERATION;
import static jp.co.stnet.cms.equipment.presentation.controller.position.PositionConstant.*;


@RequiredArgsConstructor
@Controller
@RequestMapping(BASE_PATH)
@TransactionTokenCheck(BASE_PATH)
public class PositionCreateController {

    private final PositionHelper helper;
    private final ModelMapper modelMapper;
    private final PositionAuthority authority;
    private final PositionService positionService;
    private final OperationsUtil op = new OperationsUtil(BASE_PATH);

    @ModelAttribute
    PositionForm setUp() {
        // 新規登録時の初期値設定
        return PositionForm.builder()
                .status(Status.VALID.getCodeValue()) // ステータスは有効
                .build();
    }

    /**
     * 新規作成画面を開く
     */
    @GetMapping(value = "create", params = "form")
    @TransactionTokenCheck(type = TransactionTokenType.BEGIN)
    public String createForm(PositionForm form,
                             Model model,
                             @AuthenticationPrincipal LoggedInUser loggedInUser,
                             @RequestParam(value = "copy", required = false) Long copy) {

        authority.hasAuthority(OPERATION.CREATE, loggedInUser);

        if (copy != null) {
            var source = positionService.findById(copy);
            modelMapper.map(source, form);
        }

        model.addAttribute("pageTitle", PAGE_TITLE_CREATE);
        model.addAttribute("buttonState", helper.getButtonStateMap(OPERATION.CREATE, null, form).asMap());
        model.addAttribute("fieldState", helper.getFiledStateMap(OPERATION.CREATE, null, form).asMap());
        model.addAttribute("op", op);

        return TEMPLATE_FORM;
    }

    /**
     * 新規登録
     */
    @PostMapping(value = "create")
    @TransactionTokenCheck
    public String create(@Validated({PositionForm.Create.class, Default.class}) PositionForm form,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirect,
                         @AuthenticationPrincipal LoggedInUser loggedInUser) {

        authority.hasAuthority(OPERATION.CREATE, loggedInUser);

        if (bindingResult.hasErrors()) {
            return createForm(form, model, loggedInUser, null);
        }

        var entity = modelMapper.map(form, Position.class);

        try {
            positionService.save(entity);

        } catch (BusinessException e) {
            model.addAttribute(e.getResultMessages());
            return createForm(form, model, loggedInUser, null);
        }

        ResultMessages messages = ResultMessages.info().add(MessageKeys.I_CM_FW_0001);
        redirect.addFlashAttribute(messages);

        return "redirect:" + op.getEditUrl(entity.getPositionId().toString());
    }
}