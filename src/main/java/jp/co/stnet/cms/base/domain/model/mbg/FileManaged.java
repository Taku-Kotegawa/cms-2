package jp.co.stnet.cms.base.domain.model.mbg;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.LocalDateTime;
import jp.co.stnet.cms.base.domain.model.KeyInterface;
import jp.co.stnet.cms.base.domain.model.StatusInterface;
import jp.co.stnet.cms.base.domain.model.VersionInterface;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Database Table Remarks:
 *   ファイルマネージドエンティティ
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_file_managed
 */
@Data
@SuperBuilder
@EqualsAndHashCode
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class FileManaged implements Serializable, KeyInterface<Long>, VersionInterface, StatusInterface {
    /**
     * Database Column Remarks:
     *   ファイルマネージドid
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_file_managed.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     * Database Column Remarks:
     *   ファイルを一意に特定する番号
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_file_managed.uuid
     *
     * @mbg.generated
     */
    private String uuid;

    /**
     * Database Column Remarks:
     *   バージョン
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_file_managed.version
     *
     * @mbg.generated
     */
    private Long version;

    /**
     * Database Column Remarks:
     *   ステータス
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_file_managed.status
     *
     * @mbg.generated
     */
    private String status;

    /**
     * Database Column Remarks:
     *   作成者
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_file_managed.created_by
     *
     * @mbg.generated
     */
    private String createdBy;

    /**
     * Database Column Remarks:
     *   作成日時
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_file_managed.created_date
     *
     * @mbg.generated
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime createdDate;

    /**
     * Database Column Remarks:
     *   最終更新者
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_file_managed.last_modified_by
     *
     * @mbg.generated
     */
    private String lastModifiedBy;

    /**
     * Database Column Remarks:
     *   最終更新日
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_file_managed.last_modified_date
     *
     * @mbg.generated
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime lastModifiedDate;

    /**
     * Database Column Remarks:
     *   ファイル名
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_file_managed.original_filename
     *
     * @mbg.generated
     */
    private String originalFilename;

    /**
     * Database Column Remarks:
     *   uri
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_file_managed.uri
     *
     * @mbg.generated
     */
    private String uri;

    /**
     * Database Column Remarks:
     *   mimeタイプ
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_file_managed.file_mime
     *
     * @mbg.generated
     */
    private String fileMime;

    /**
     * Database Column Remarks:
     *   ファイルサイズ
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_file_managed.file_size
     *
     * @mbg.generated
     */
    private Long fileSize;

    /**
     * Database Column Remarks:
     *   ファイルの種類
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_file_managed.file_type
     *
     * @mbg.generated
     */
    private String fileType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_file_managed
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }
}