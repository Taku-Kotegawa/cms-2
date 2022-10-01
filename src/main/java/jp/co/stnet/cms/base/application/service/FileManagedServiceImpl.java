package jp.co.stnet.cms.base.application.service;


import jp.co.stnet.cms.base.application.repository.FileManagedRepository;
import jp.co.stnet.cms.base.domain.enums.FileStatus;
import jp.co.stnet.cms.base.domain.enums.FileType;
import jp.co.stnet.cms.base.domain.model.mbg.FileManaged;
import jp.co.stnet.cms.common.message.MessageKeys;
import jp.co.stnet.cms.common.util.MimeTypes;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ContentDisposition;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;
import org.terasoluna.gfw.common.message.ResultMessages;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.apache.commons.text.StringEscapeUtils.escapeHtml4;

@Slf4j
@Service
@Transactional
public class FileManagedServiceImpl implements FileManagedService {

    @Autowired
    FileManagedRepository fileManagedRepository;

    @Value("${file.store.basedir}")
    String fileStoreBasedir;

    @Override
    @Transactional(readOnly = true)
    public byte[] getFile(String id) {
        String filePath = fileStoreBasedir + findById(id).getUri();
        try {
            return Files.readAllBytes(Paths.get(filePath));
        } catch (IOException e) {
            throw new ResourceNotFoundException(ResultMessages.error().add(MessageKeys.E_SL_FW_5001, filePath));
        }
    }

    @Transactional(readOnly = true)
    public FileManaged findById(String id) {
        return fileManagedRepository.findById(id).orElse(null);
    }

    @Override
    public FileManaged store(MultipartFile file, FileType fileType) throws IOException {

        if (file == null) {
            throw new IllegalArgumentException("file must not be null");
        }

        if (fileType == null) {
            fileType = FileType.DEFAULT;
        }

        var uuid = UUID.randomUUID().toString();
        var storeDir = getStoreDir(uuid, fileType);
        var storeFilePath = getStoreFilePath(uuid, storeDir);
        var mimeType = MimeTypes.getMimeType(FilenameUtils.getExtension(file.getOriginalFilename()));

        // 保存先ディレクトリ作成
        Files.createDirectories(Path.of(storeDir));

        // ファイルを保存
        storeMultiPartFile(file, storeFilePath);

        // FileManagedの登録
        FileManaged fileManaged = FileManaged.builder()
                .uuid(uuid)
                .fileType(fileType.name())
                .originalFilename(file.getOriginalFilename())
                .fileMime(mimeType)
                .fileSize(file.getSize())
                .status(FileStatus.TEMPORARY.getCodeValue())
                .uri(storeFilePath.substring(fileStoreBasedir.length()).replace('\\', '/'))
                .build();
        return fileManagedRepository.register(fileManaged);

    }

    @Override
    public FileManaged store(Path path, FileType fileType) throws IOException {

        if (path == null) {
            throw new IllegalArgumentException("path must not be null");
        }

        if (fileType == null) {
            fileType = FileType.DEFAULT;
        }

        var uuid = UUID.randomUUID().toString();
        var storeDir = getStoreDir(uuid, fileType);
        var storeFilePath = getStoreFilePath(uuid, storeDir);
        var mimeType = MimeTypes.getMimeType(FilenameUtils.getExtension(path.getFileName().toString()));

        // 保存先ディレクトリ作成
        Files.createDirectories(Path.of(storeDir));

        // ファイルを保存
        Files.copy(path, new File(storeFilePath).toPath());

        // FileManagedの登録
        FileManaged fileManaged = FileManaged.builder()
                .uuid(uuid)
                .fileType(fileType.name())
                .originalFilename(path.getFileName().toString())
                .fileMime(mimeType)
                .fileSize(Files.size(path))
                .status(FileStatus.TEMPORARY.getCodeValue())
                .uri(storeFilePath.substring(fileStoreBasedir.length()).replace('\\', '/'))
                .build();
        return fileManagedRepository.register(fileManaged);
    }

    // MultiPartFileを保存する
    private void storeMultiPartFile(MultipartFile file, String storeFilePath) throws IOException {
        var storeFile = new File(storeFilePath);
        try (BufferedOutputStream fileStream = new BufferedOutputStream(new FileOutputStream(storeFile))) {
            fileStream.write(file.getBytes());
        }
    }


    private String getStoreDir(String uuid, FileType fileType) {
        if (fileType == null) {
            fileType = FileType.DEFAULT;
        }
        return fileStoreBasedir
                + File.separator + fileType
                + File.separator + uuid.charAt(0);
    }

    private String getStoreFilePath(String uuid, String storeDir) {
        return storeDir + File.separator + uuid;
    }


    @Override
    public FileManaged permanent(String id) {
        var fileManaged = fileManagedRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("id = " + id));
        fileManaged.setStatus(FileStatus.PERMANENT.getCodeValue());
        return fileManagedRepository.save(fileManaged);
    }

    @Override
    public void delete(String id) {
        var fileManaged = fileManagedRepository.findById(id).orElse(null);
        if (fileManaged != null) {
            // 物理ファイル削除
            deleteFile(fileManaged.getUri());
            fileManagedRepository.deleteById(id);
        }
    }

    @Override
    public void cleanup(LocalDateTime deleteTo) {
        List<FileManaged> files = fileManagedRepository.findAllByCreatedDateLessThanAndStatus(deleteTo, FileStatus.TEMPORARY.getCodeValue());
        for (FileManaged file : files) {
            delete(file.getId());
        }

        //TODO 空のフォルダを削除

    }

    @Override
    @Transactional(readOnly = true)
    public String getFileStoreBaseDir() {
        return fileStoreBasedir + "/";
    }

    @Override
    @Transactional(readOnly = true)
    public String getContent(String id) throws IOException, TikaException {
        Tika tika = new Tika();
        tika.setMaxStringLength(1000 * 1000); // 1M
        return tika.parseToString(new FileInputStream(new File(getFileStoreBaseDir() + findById(id).getUri())));
    }

    @Override
    public FileManaged copy(String sourceUuid) throws IOException {
        var fileManaged = fileManagedRepository.findById(sourceUuid)
                .orElseThrow(() -> new IllegalArgumentException("id = " + sourceUuid));
        Path path = Path.of(fileManaged.getUri());
        var newFileManaged = store(path, FileType.valueOf(fileManaged.getFileType()));
        if (!fileManaged.getStatus().equals(newFileManaged.getStatus())) {
            newFileManaged.setStatus(fileManaged.getStatus());
            newFileManaged = fileManagedRepository.save(newFileManaged);
        }
        return newFileManaged;
    }

    @Override
    public void deleteFile(String uri) {
        if (uri != null) {
            String filePath = fileStoreBasedir + uri.replace('/', '\\');
            try {
                Files.delete(Path.of(filePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String escapeContent(String rawContent) {
        rawContent = rawContent.replaceAll("[ |　|\\n|\\r\\n|\\r|\t]+", " ");
        return escapeHtml4(rawContent);
    }


    @Override
    public ContentDisposition getAttachmentContentDisposition(FileManaged fileManaged) {
        Objects.requireNonNull(fileManaged);
        var originalFilename = fileManaged.getOriginalFilename();
        if (originalFilename != null) {
            String encodedFilename = URLEncoder.encode(originalFilename, StandardCharsets.UTF_8).replace("+", "%20");
            if (isOpenWindows(fileManaged)) {
                return ContentDisposition.builder("filename=\"" + encodedFilename + "\"").build();
            } else {
                return ContentDisposition.builder("attachment;filename=\"" + originalFilename + "\";filename*=UTF-8'ja'" + encodedFilename).build();
            }
        } else {
            return null;
        }
    }

    public MediaType getMediaType(FileManaged fileManaged) {
        if (fileManaged.getFileMime() != null) {
            String[] mimeArray = fileManaged.getFileMime().split("/");
            return new MediaType(mimeArray[0], mimeArray[1]);
        } else {
            return null;
        }
    }

    /**
     * ダウンロード時にタブで開くかファイル保存か
     *
     * @return true:タブで開く, false:ファイル保存
     */
    private boolean isOpenWindows(FileManaged fileManaged) {
        return MediaType.APPLICATION_PDF_VALUE.equals(fileManaged.getFileMime());
    }

}
