package jp.co.stnet.cms.equipment.domain.model.mbg;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import jp.co.stnet.cms.base.domain.model.KeyInterface;
import jp.co.stnet.cms.base.domain.model.VersionInterface;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Database Table Remarks:
 *   購入依頼
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table request_equip
 */
@Data
@SuperBuilder
@EqualsAndHashCode
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class RequestEquip implements Serializable, KeyInterface<Long>, VersionInterface {
    /**
     * Database Column Remarks:
     *   購入依頼ID
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column request_equip.request_id
     *
     * @mbg.generated
     */
    private Long requestId;

    /**
     * Database Column Remarks:
     *   作成者
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column request_equip.created_by
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
     * This field corresponds to the database column request_equip.created_date
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
     * This field corresponds to the database column request_equip.last_modified_by
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
     * This field corresponds to the database column request_equip.last_modified_date
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
     * This field corresponds to the database column request_equip.version
     *
     * @mbg.generated
     */
    @EqualsAndHashCode.Exclude
    private Long version;

    /**
     * Database Column Remarks:
     *   購入品名
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column request_equip.equip_name
     *
     * @mbg.generated
     */
    private String equipName;

    /**
     * Database Column Remarks:
     *   備考
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column request_equip.note
     *
     * @mbg.generated
     */
    private String note;

    /**
     * Database Column Remarks:
     *   ProgressStateID
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column request_equip.progress_state
     *
     * @mbg.generated
     */
    private Short progressState;

    /**
     * Database Column Remarks:
     *   購入依頼理由
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column request_equip.reason
     *
     * @mbg.generated
     */
    private String reason;

    /**
     * Database Column Remarks:
     *   購入依頼日
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column request_equip.request_date
     *
     * @mbg.generated
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private LocalDate requestDate;

    /**
     * Database Column Remarks:
     *   承認フローID
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column request_equip.approval_flow_id
     *
     * @mbg.generated
     */
    private Long approvalFlowId;

    /**
     * Database Column Remarks:
     *   備品タイプ
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column request_equip.equip_type_id
     *
     * @mbg.generated
     */
    private Long equipTypeId;

    /**
     * Database Column Remarks:
     *   購入依頼者
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column request_equip.requester_id
     *
     * @mbg.generated
     */
    private String requesterId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table request_equip
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return requestId;
    }
}