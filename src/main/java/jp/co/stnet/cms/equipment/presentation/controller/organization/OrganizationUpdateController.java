package jp.co.stnet.cms.equipment.presentation.controller.organization;

import jp.co.stnet.cms.base.domain.model.LoggedInUser;
import jp.co.stnet.cms.common.message.MessageKeys;
import jp.co.stnet.cms.common.util.OperationsUtil;
import jp.co.stnet.cms.equipment.application.service.OrganizationService;
import jp.co.stnet.cms.equipment.domain.model.OrganizationBean;
import jp.co.stnet.cms.equipment.presentation.dto.OrganizationForm;
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
import static jp.co.stnet.cms.equipment.presentation.controller.organization.OrganizationConstant.BASE_PATH;
import static jp.co.stnet.cms.equipment.presentation.controller.organization.OrganizationConstant.TEMPLATE_FORM;


@RequiredArgsConstructor
@Controller
@RequestMapping(BASE_PATH)
@TransactionTokenCheck(BASE_PATH)
public class OrganizationUpdateController {

    private final OrganizationService organizationService;
    private final OrganizationHelper helper;
    private final OrganizationAuthority authority;
    private final ModelMapper modelMapper;
    private final OperationsUtil op = new OperationsUtil(BASE_PATH);

    @ModelAttribute
    OrganizationForm setUp() {
        return new OrganizationForm();
    }

    /**
     * 編集画面を開く
     */
    @GetMapping(value = "{id}/update", params = "form")
    @TransactionTokenCheck(type = TransactionTokenType.BEGIN)
    public String updateForm(OrganizationForm form, Model model, @AuthenticationPrincipal LoggedInUser loggedInUser,
                             @PathVariable("id") Long id) {


        // 実行権限が無い場合、AccessDeniedExceptionをスローし、キャッチしないと権限エラー画面に遷移
        authority.hasAuthority(OPERATION.UPDATE, loggedInUser);

        var entity = organizationService.findById(id);
        var organizationBean = OrganizationBean.of(entity);

        // 初回表示(入力チェックエラー時の再表示でない場合)
        if (form.getVersion() == null) {
            modelMapper.map(organizationBean, form);
        }

        model.addAttribute("pageTitle", "部署 編集");
        model.addAttribute("organizationBean", organizationBean);
        model.addAttribute("buttonState", helper.getButtonStateMap(OPERATION.UPDATE, organizationBean, form).asMap());
        model.addAttribute("fieldState", helper.getFiledStateMap(OPERATION.UPDATE, organizationBean, form).asMap());
        model.addAttribute("op", op);

        return TEMPLATE_FORM;
    }

    /**
     * 更新
     */
    @PostMapping(value = "{id}/update")
    @TransactionTokenCheck
    public String update(@Validated({OrganizationForm.Update.class, Default.class}) OrganizationForm form,
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
        var entity = organizationService.findById(id);
        modelMapper.map(form, entity);

        try {
            organizationService.save(entity);
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
            organizationService.invalid(id);
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
            organizationService.valid(id);
        } catch (BusinessException e) {
            model.addAttribute(e.getResultMessages());
        }

        ResultMessages messages = ResultMessages.info().add(MessageKeys.I_CM_FW_0002);
        redirect.addFlashAttribute(messages);

        return "redirect:" + op.getEditUrl(id.toString());
    }

}