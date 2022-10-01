package jp.co.stnet.cms.base.presentation.controller.authentication.account;


import jp.co.stnet.cms.base.application.service.AccountSharedService;
import jp.co.stnet.cms.base.application.service.FileManagedService;
import jp.co.stnet.cms.base.domain.model.Account;
import jp.co.stnet.cms.base.domain.model.LoggedInUser;
import jp.co.stnet.cms.base.domain.model.mbg.FileManaged;
import jp.co.stnet.cms.common.message.MessageKeys;
import org.apache.commons.io.IOUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.message.ResultMessages;

import javax.servlet.http.HttpServletResponse;
import javax.validation.groups.Default;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

@Controller
@RequestMapping("account")
public final class AccountController {

    @Autowired
    AccountSharedService accountSharedService;

    @Autowired
    FileManagedService fileManagedService;

    @Value(value = "classpath:images/nobody.png")
    Resource nobodyImage;

    @Autowired
    ModelMapper beanMapper;

    @ModelAttribute
    public AccountCreateForm setUpAccountCreateForm() {
        return new AccountCreateForm();
    }

    @GetMapping
    public String view(@AuthenticationPrincipal LoggedInUser userDetails,
                       Model model) {
        Account account = userDetails.getAccount();
        model.addAttribute("account", account);
        return "account/view";
    }

    @GetMapping("/image")
    @ResponseBody
    public ResponseEntity<byte[]> showImage(
            @AuthenticationPrincipal LoggedInUser loggedInUser, final HttpServletResponse response)
            throws IOException {

        response.addHeader("Cache-Control", "max-age=60, must-revalidate, no-transform");

        FileManaged fileManaged = accountSharedService.getImage(loggedInUser.getUsername());

        HttpHeaders headers = new HttpHeaders();

        if (fileManaged != null) {
            headers.setContentType(fileManagedService.getMediaType(fileManaged));
            return new ResponseEntity<byte[]>(
                    fileManagedService.getFile(fileManaged.getId()),
                    headers,
                    HttpStatus.OK);
        } else {
            InputStream ins = nobodyImage.getInputStream();
            headers.setContentType(MediaType.IMAGE_PNG);
            return new ResponseEntity<byte[]>(
                    IOUtils.toByteArray(ins),
                    headers,
                    HttpStatus.OK);
        }

    }

    @GetMapping(value = "/create", params = "form")
    public String createForm() {
        return "account/accountCreateForm";
    }

    @PostMapping(value = "/create", params = "redo")
    public String redoCreateForm(AccountCreateForm form) {
        return "account/accountCreateForm";
    }

    @PostMapping(value = "/create", params = "confirm")
    public String createConfirm(
            @Validated({AccountCreateForm.Confirm.class, Default.class}) AccountCreateForm form,
            BindingResult result, Model model,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return createForm();
        }
        if (accountSharedService.exists(form.getUsername())) {
            model.addAttribute(ResultMessages.error().add(
                    MessageKeys.E_SL_AC_5001));
            return createForm();
        }
//        try {
//            TempFile tempFile = new TempFile();
//            tempFile.setBody(form.getImage().getBytes());
//            tempFile.setOriginalName(form.getImage().getOriginalFilename());
//            String fileId = fileUploadSharedService.uploadTempFile(tempFile);
//            form.setImageId(fileId);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        redirectAttributes.addFlashAttribute("accountCreateForm", form);
        return "account/accountConfirm";
    }

    @PostMapping("/create")
    public String create(
            @Validated({AccountCreateForm.CreateAccount.class, Default.class}) AccountCreateForm form,
            BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return createForm();
        }
        Account account = beanMapper.map(form, Account.class);
        account.setRoles(Arrays.asList("USER"));

        String password = accountSharedService.create(account);
        redirectAttributes.addFlashAttribute("firstName", form.getFirstName());
        redirectAttributes.addFlashAttribute("lastName", form.getLastName());
        redirectAttributes.addFlashAttribute("password", password);
        return "redirect:/account/create?complete";
    }

    @GetMapping(value = "/create", params = "complete")
    public String createComplete() {
        return "account/createComplete";
    }


}
