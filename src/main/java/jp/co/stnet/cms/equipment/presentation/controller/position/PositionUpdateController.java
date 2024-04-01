package jp.co.stnet.cms.equipment.presentation.controller.position;

import jp.co.stnet.cms.base.domain.model.LoggedInUser;
import jp.co.stnet.cms.common.message.MessageKeys;
import jp.co.stnet.cms.common.util.OperationsUtil;
import jp.co.stnet.cms.equipment.application.service.PositionService;
import jp.co.stnet.cms.equipment.domain.model.PositionBean;
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
public class PositionUpdateController {

    private final PositionService positionService;
    private final PositionHelper helper;
    private final PositionAuthority authority;
    private final ModelMapper modelMapper;
    private final OperationsUtil op = new OperationsUtil(BASE_PATH);

    @ModelAttribute
    PositionForm setUp() {
        return new PositionForm();
    }

    /**
     * 編集画面を開く
     */
    @GetMapping(value = "{id}/update", params = "form")
    @TransactionTokenCheck(type = TransactionTokenType.BEGIN)
    public String updateForm(PositionForm form, Model model, @AuthenticationPrincipal LoggedInUser loggedInUser,
                             @PathVariable("id") Long id) {


        // 実行権限が無い場合、AccessDeniedExceptionをスローし、キャッチしないと権限エラー画面に遷移
        authority.hasAuthority(OPERATION.UPDATE, loggedInUser);

        var entity = positionService.findById(id);
        var positionBean = PositionBean.of(entity);

        // 初回表示(入力チェックエラー時の再表示でない場合)
        if (form.getVersion() == null) {
            modelMapper.map(positionBean, form);
        }

        model.addAttribute("pageTitle", PAGE_TITLE_UPDATE);
        model.addAttribute("positionBean", positionBean);
        model.addAttribute("buttonState", helper.getButtonStateMap(OPERATION.UPDATE, positionBean, form).asMap());
        model.addAttribute("fieldState", helper.getFiledStateMap(OPERATION.UPDATE, positionBean, form).asMap());
        model.addAttribute("op", op);

        return TEMPLATE_FORM;
    }

    /**
     * 更新
     */
    @PostMapping(value = "{id}/update")
    @TransactionTokenCheck
    public String update(@Validated({PositionForm.Update.class, Default.class}) PositionForm form,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirect,
                         @AuthenticationPrincipal LoggedInUser loggedInUser,
                         @PathVariable("id") Long id) {

        // 実行権限が無い場合、AccessDeniedExceptionをスローし、キャッチしないと権限エラー画面に遷移
        authority.hasAuthority(OPERATION.UPDATE, loggedInUser);

        if (bindingResult.hasErrors()) {
            return updateForm(form, model, loggedInUser, id);
        }

        // 存在確認
        var entity = positionService.findById(id);
        modelMapper.map(form, entity);

        try {
            positionService.save(entity);
        } catch (BusinessException e) {
            model.addAttribute(e.getResultMessages());
            return updateForm(form, model, loggedInUser, id);
        }

        ResultMessages messages = ResultMessages.info().add(MessageKeys.I_CM_FW_0004);
        redirect.addFlashAttribute(messages);

        return "redirect:" + op.getEditUrl(entity.getPrimaryKey().toString());
    }

    // --- 無効化 -------------------------------------------------------------------------------------------------------

    @GetMapping(value = "{id}/invalid")
    @TransactionTokenCheck(type = TransactionTokenType.BEGIN)
    public String invalid(Model model, RedirectAttributes redirect, @AuthenticationPrincipal LoggedInUser loggedInUser,
                          @PathVariable("id") Long id) {

        // 実行権限が無い場合、AccessDeniedExceptionをスローし、キャッチしないと権限エラー画面に遷移
        authority.hasAuthority(OPERATION.INVALID, loggedInUser);

        try {
            positionService.invalid(id);
        } catch (BusinessException e) {
            model.addAttribute(e.getResultMessages());
        }

        ResultMessages messages = ResultMessages.info().add(MessageKeys.I_CM_FW_0002);
        redirect.addFlashAttribute(messages);

        return "redirect:" + op.getEditUrl(id.toString());
    }

    // --- 無効解除 -----------------------------------------------------------------------------------------------------

    @GetMapping(value = "{id}/valid")
    @TransactionTokenCheck(type = TransactionTokenType.BEGIN)
    public String valid(Model model, RedirectAttributes redirect, @AuthenticationPrincipal LoggedInUser loggedInUser,
                        @PathVariable("id") Long id) {

        // 実行権限が無い場合、AccessDeniedExceptionをスローし、キャッチしないと権限エラー画面に遷移
        authority.hasAuthority(OPERATION.VALID, loggedInUser);

        try {
            positionService.valid(id);
        } catch (BusinessException e) {
            model.addAttribute(e.getResultMessages());
        }

        ResultMessages messages = ResultMessages.info().add(MessageKeys.I_CM_FW_0002);
        redirect.addFlashAttribute(messages);

        return "redirect:" + op.getEditUrl(id.toString());
    }

}