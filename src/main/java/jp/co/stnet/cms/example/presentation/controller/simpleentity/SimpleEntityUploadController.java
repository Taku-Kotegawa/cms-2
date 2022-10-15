package jp.co.stnet.cms.example.presentation.controller.simpleentity;

import jp.co.stnet.cms.base.application.service.FileManagedService;
import jp.co.stnet.cms.base.domain.model.LoggedInUser;
import jp.co.stnet.cms.base.domain.model.mbg.FileManaged;
import jp.co.stnet.cms.base.presentation.controller.admin.upload.UploadForm;
import jp.co.stnet.cms.base.presentation.controller.job.JobStarter;
import jp.co.stnet.cms.common.constant.Constants;
import jp.co.stnet.cms.common.util.OperationsUtil;
import jp.co.stnet.cms.common.util.StateMap;
import jp.co.stnet.cms.example.application.service.SimpleEntityService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

import static jp.co.stnet.cms.example.presentation.controller.simpleentity.SimpleEntityConstant.*;

@RequiredArgsConstructor
@Controller
@RequestMapping(BASE_PATH)
public class SimpleEntityUploadController {

    private final SimpleEntityService simpleEntityService;
    private final SimpleEntityHelper helper;
    private final SimpleEntityAuthority authority;
    private final FileManagedService fileManagedService;
    private final ModelMapper modelMapper;
    private final JobStarter jobStarter;


    /**
     * アップロードファイル指定画面の表示
     */
    @GetMapping(value = "upload", params = "form")
    public String uploadForm(@ModelAttribute UploadForm form, Model model,
                             @AuthenticationPrincipal LoggedInUser loggedInUser) {

        authority.hasAuthority(Constants.OPERATION.UPLOAD, loggedInUser);

        form.setJobName(UPLOAD_JOB_ID);

        model.addAttribute("pageTitle", "Import SimpleEntity");
        model.addAttribute("referer", "list");
        model.addAttribute("fieldState", new StateMap(UploadForm.class, new ArrayList<>(), new ArrayList<>()).setInputTrueAll().asMap());
        model.addAttribute("op", new OperationsUtil(BASE_PATH));

        return TEMPLATE_UPLOAD_FORM;
    }

    /**
     * アップロード処理(バッチ実行)
     */
    @PostMapping(value = "upload")
    public String upload(@Validated UploadForm form, BindingResult result, Model model,
                         RedirectAttributes redirectAttributes,
                         @AuthenticationPrincipal LoggedInUser loggedInUser) {

        authority.hasAuthority(Constants.OPERATION.UPLOAD, loggedInUser);

        final String jobName = UPLOAD_JOB_ID;

        Long jobExecutionId = null;

        if (!jobName.equals(form.getJobName()) || result.hasErrors()) {
            return uploadForm(form, model, loggedInUser);
        }

        FileManaged uploadFile = fileManagedService.findById(form.getUploadFileUuid());
        String uploadFileAbsolutePath = fileManagedService.getFileStoreBaseDir() + uploadFile.getUri();
        String jobParams = "inputFile=" + uploadFileAbsolutePath;
        jobParams += ", encoding=" + form.getEncoding();
        jobParams += ", filetype=" + form.getFileType();

        try {
            jobExecutionId = jobStarter.start(jobName, jobParams);

        } catch (JobParametersInvalidException | JobExecutionAlreadyRunningException | JobRestartException |
                 JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();

            //TODO メッセージをセットして、フォーム画面に戻る。

        }

        redirectAttributes.addAttribute("jobName", jobName);
        redirectAttributes.addAttribute("jobExecutionId", jobExecutionId);

        return "redirect:upload?complete";
    }

    /**
     * アップロード完了画面
     */
    @GetMapping(value = "upload", params = "complete")
    public String uploadComplete(Model model,
                                 @RequestParam("jobName") String jobName,
                                 @RequestParam("jobExecutionId") String jobExecutionId,
                                 @AuthenticationPrincipal LoggedInUser loggedInUser) {

        model.addAttribute("returnBackBtn", "一覧画面に戻る");
        model.addAttribute("returnBackUrl", TEMPLATE_LIST);
        model.addAttribute("jobName", jobName);
        model.addAttribute("jobExecutionId", jobExecutionId);

        return TEMPLATE_UPLOAD_COMPLETE;
    }


}
