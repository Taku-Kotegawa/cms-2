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
 *   アカウント
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_account
 */
@Data
@SuperBuilder
@EqualsAndHashCode
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class TAccount implements Serializable, KeyInterface<String>, VersionInterface, StatusInterface {
    /**
     * Database Column Remarks:
     *   ユーザid
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_account.username
     *
     * @mbg.generated
     */
    private String username;

    /**
     * Database Column Remarks:
     *   ステータス
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_account.status
     *
     * @mbg.generated
     */
    private String status;

    /**
     * Database Column Remarks:
     *   バージョン
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_account.version
     *
     * @mbg.generated
     */
    @EqualsAndHashCode.Exclude
    private Long version;

    /**
     * Database Column Remarks:
     *   作成者
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_account.created_by
     *
     * @mbg.generated
     */
    @EqualsAndHashCode.Exclude
    private String createdBy;

    /**
     * Database Column Remarks:
     *   作成日時
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_account.created_date
     *
     * @mbg.generated
     */
    @EqualsAndHashCode.Exclude
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime createdDate;

    /**
     * Database Column Remarks:
     *   最終更新者
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_account.last_modified_by
     *
     * @mbg.generated
     */
    @EqualsAndHashCode.Exclude
    private String lastModifiedBy;

    /**
     * Database Column Remarks:
     *   最終更新日時
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_account.last_modified_date
     *
     * @mbg.generated
     */
    @EqualsAndHashCode.Exclude
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime lastModifiedDate;

    /**
     * Database Column Remarks:
     *   パスワード
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_account.password
     *
     * @mbg.generated
     */
    private String password;

    /**
     * Database Column Remarks:
     *   名
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_account.first_name
     *
     * @mbg.generated
     */
    private String firstName;

    /**
     * Database Column Remarks:
     *   姓
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_account.last_name
     *
     * @mbg.generated
     */
    private String lastName;

    /**
     * Database Column Remarks:
     *   メールアドレス
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_account.email
     *
     * @mbg.generated
     */
    private String email;

    /**
     * Database Column Remarks:
     *   所属部門
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_account.department
     *
     * @mbg.generated
     */
    private String department;

    /**
     * Database Column Remarks:
     *   url
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_account.url
     *
     * @mbg.generated
     */
    private String url;

    /**
     * Database Column Remarks:
     *   プロフィール
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_account.profile
     *
     * @mbg.generated
     */
    private String profile;

    /**
     * Database Column Remarks:
     *   イメージファイルUUID
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_account.image_uuid
     *
     * @mbg.generated
     */
    private String imageUuid;

    /**
     * Database Column Remarks:
     *   ログイン許可ipアドレス
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_account.allowed_ip
     *
     * @mbg.generated
     */
    private String allowedIp;

    /**
     * Database Column Remarks:
     *   api key
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_account.api_key
     *
     * @mbg.generated
     */
    private String apiKey;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_account
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    public String getId() {
        return username;
    }
}