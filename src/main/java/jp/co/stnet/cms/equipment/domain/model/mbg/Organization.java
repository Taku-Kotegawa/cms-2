package jp.co.stnet.cms.equipment.domain.model.mbg;

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
 *   部署
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table organization
 */
@Data
@SuperBuilder
@EqualsAndHashCode
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class Organization implements Serializable, KeyInterface<Long>, VersionInterface, StatusInterface {
    /**
     * Database Column Remarks:
     *   部署ID
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column organization.organization_id
     *
     * @mbg.generated
     */
    private Long organizationId;

    /**
     * Database Column Remarks:
     *   作成者
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column organization.created_by
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
     * This field corresponds to the database column organization.created_date
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
     * This field corresponds to the database column organization.last_modified_by
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
     * This field corresponds to the database column organization.last_modified_date
     *
     * @mbg.generated
     */
    @EqualsAndHashCode.Exclude
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime lastModifiedDate;

    /**
     * Database Column Remarks:
     *   バージョン(排他制御用)
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column organization.version
     *
     * @mbg.generated
     */
    @EqualsAndHashCode.Exclude
    private Long version;

    /**
     * Database Column Remarks:
     *   部門長ID
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column organization.director_id
     *
     * @mbg.generated
     */
    private Long directorId;

    /**
     * Database Column Remarks:
     *   部署名
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column organization.group_name
     *
     * @mbg.generated
     */
    private String groupName;

    /**
     * Database Column Remarks:
     *   親部署ID
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column organization.parent_id
     *
     * @mbg.generated
     */
    private Long parentId;

    /**
     * Database Column Remarks:
     *   ステータス
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column organization.status
     *
     * @mbg.generated
     */
    private String status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table organization
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return organizationId;
    }
}