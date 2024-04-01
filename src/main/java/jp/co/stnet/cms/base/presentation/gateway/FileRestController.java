package jp.co.stnet.cms.base.presentation.gateway;


import jp.co.stnet.cms.base.application.service.FileManagedService;
import jp.co.stnet.cms.base.domain.enums.FileType;
import jp.co.stnet.cms.base.domain.model.LoggedInUser;
import jp.co.stnet.cms.base.domain.model.mbg.FileManaged;
import jp.co.stnet.cms.base.presentation.controller.uploadfile.UploadFileResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("api/file")
public class FileRestController {

    private final FileManagedService fileManagedService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public UploadFileResult store(
            @RequestParam("file") MultipartFile multipartFile,
            @RequestParam(value = "filetype", required = false) String type,
            @AuthenticationPrincipal LoggedInUser loggedInUser,
            HttpServletRequest request) {

        try {

            FileType fileType = FileType.getByValue(type);
            if (fileType == null) {
                fileType = FileType.DEFAULT;
            }

            // ファイルサイズのチェック
            if (fileType.getFileSize() != null && !fileType.getFileSize().isEmpty()) {
                if (Long.parseLong(fileType.getFileSize()) * 1024 * 1024 < multipartFile.getSize()) {
                    return UploadFileResult.builder()
                            .message("Upload Fail. [ファイルが大きすぎます。(" + fileType.getFileSize() + "MBまで)]")
                            .build();
                }
            }

            // ファイルの拡張子チェック
            if (fileType.getExtensionPattern() != null && !fileType.getExtensionPattern().isEmpty()) {
                String extension = multipartFile.getOriginalFilename()
                        .substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);

                String[] patterns = fileType.getExtensionPattern().split(";");
                boolean find = false;
                for (String pattern : patterns) {
                    if (extension.equals(pattern)) {
                        find = true;
                        break;
                    }
                }

                if (!find) {
                    return UploadFileResult.builder()
                            .message("Upload Fail. [指定されたファイルは選択できません。(" + fileType.getExtensionPattern() + ")]")
                            .build();
                }
            }

            FileManaged fileManaged = fileManagedService.store(multipartFile, FileType.valueOf(type));

            return UploadFileResult.builder()
                    .uuid(fileManaged.getUuid())
                    .name(fileManaged.getOriginalFilename())
                    .type(fileManaged.getFileMime())
                    .size(fileManaged.getFileSize())
                    .message("Upload Success.")
                    .url(fileManaged.getUuid() + "/download")
                    .build();

        } catch (Exception e) {
            return UploadFileResult.builder()
                    .message("Upload Fail. [" + e.getMessage() + "]")
                    .build();
        }

    }

}
