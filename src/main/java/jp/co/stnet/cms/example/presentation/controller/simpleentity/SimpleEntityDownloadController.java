package jp.co.stnet.cms.example.presentation.controller.simpleentity;

import jp.co.stnet.cms.base.application.service.FileManagedService;
import jp.co.stnet.cms.base.domain.model.LoggedInUser;
import jp.co.stnet.cms.base.domain.model.mbg.FileManaged;
import jp.co.stnet.cms.common.constant.Constants;
import jp.co.stnet.cms.example.application.service.SimpleEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static jp.co.stnet.cms.example.presentation.controller.simpleentity.SimpleEntityConstant.BASE_PATH;


@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping(BASE_PATH)
public class SimpleEntityDownloadController {

    private final SimpleEntityAuthority authority;
    private final FileManagedService fileManagedService;


    /**
     * ファイルダウンロード
     */
    @GetMapping("{uuid}/download")
    public String download(
            Model model,
            @PathVariable("uuid") String uuid,
            @AuthenticationPrincipal LoggedInUser loggedInUser) {

        authority.hasAuthority(Constants.OPERATION.DOWNLOAD, loggedInUser);

        FileManaged fileManaged = fileManagedService.findById(uuid);
        model.addAttribute(fileManaged);

        return "fileManagedDownloadView";
    }

}
