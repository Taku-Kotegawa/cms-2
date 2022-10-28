package jp.co.stnet.cms.base.application.service;


import jp.co.stnet.cms.base.domain.enums.FileType;
import jp.co.stnet.cms.base.domain.model.mbg.FileManaged;
import org.apache.tika.exception.TikaException;
import org.springframework.http.ContentDisposition;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;

/**
 * ファイルを操作するクラス
 */
public interface FileManagedService {

    /**
     * idでファイルの内容をbyte[]で取得する。
     *
     * @param uuid uuid
     * @return ファイル
     */
    byte[] getFile(String uuid);

    /**
     * idでFileManagedを検索する。
     *
     * @param uuid uuid
     * @return FileManaged
     */
    FileManaged findById(String uuid);

    /**
     *
     * @param uuid uuid
     * @return FileManaged, uuid = null -> null
     */
    FileManaged findByIdOrNull(String uuid);

    /**
     * ファイルを保存する。(一時保存)
     *
     * @param file     保存するファイル(MultipartFile)
     * @param fileType ファイルタイプ
     * @return FileManaged
     * @throws IOException ファイル操作例外
     */
    FileManaged store(MultipartFile file, FileType fileType) throws IOException;

    /**
     * ファイルを保存する。(一時保存)
     *
     * @param path     保存するファイル(Path)
     * @param fileType ファイルタイプ
     * @return FileManaged
     * @throws IOException ファイル操作例外
     */
    FileManaged store(Path path, FileType fileType) throws IOException;

    /**
     * ファイルのステータスを一時保存から永久保存に変更する。
     *
     * @param uuid uuid
     */
    FileManaged permanent(String uuid);

    /**
     * idでFileManagedとファイルを削除する。
     *
     * @param uuid uuid
     */
    void delete(String uuid);

    /**
     * 指定日時以前の一時保存状態のFileManagedとファイルを削除する。
     *
     * @param deleteTo 日時
     */
    void cleanup(LocalDateTime deleteTo);

    /**
     * ファイルを保存するディレクトリを取得する。
     *
     * @return ディレクトリ
     */
    String getFileStoreBaseDir();

    /**
     * ファイルの内容を取得する。
     *
     * @param uuid UUID
     * @return ファイルの内容
     * @throws IOException ファイルの読み込みに失敗する場合
     */
    String getContent(String uuid) throws IOException, TikaException;

    /**
     * FileManagedとファイルをコピーし、新しいUUIDを発番する。
     *
     * @param uuid UUID
     * @return コピー後のFileManaged
     * @throws IOException ファイルの操作に失敗した場合
     */
    FileManaged copy(String uuid) throws IOException;

    /**
     * 指定したURIでファイルを削除する。(物理ファイルのみ)
     *
     * @param uri URI
     */
    void deleteFile(String uri);

//    /**
//     * HTMLエスケープおよび連続する空白の除去
//     *
//     * @param rawContent 文字列
//     * @return エスケースされた文字列
//     */
//    String escapeContent(String rawContent);


    /**
     * ContentDisposition を取得する
     *
     * @param fileManaged FileManaged
     * @return ContentDisposition
     */
    ContentDisposition getAttachmentContentDisposition(FileManaged fileManaged);


    MediaType getMediaType(FileManaged fileManaged);

    /**
     * @param uuid
     * @param username
     * @return
     */
    FileManaged findByIdAndCreatedBy(String uuid, String username);


    /**
     * @param uuid
     * @param fileType
     * @return
     */
    FileManaged findByIdAndFileType(String uuid, FileType fileType);

    /**
     * @param uuid
     * @param fileType
     * @param username
     * @return
     */
    FileManaged findByIdAndFileTypeAndCreatedBy(String uuid, FileType fileType, String username);

}
