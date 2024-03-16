package jp.co.stnet.cms.equipment.domain.model.mbg;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.LocalDateTime;
import jp.co.stnet.cms.base.domain.model.StatusInterface;
import jp.co.stnet.cms.base.domain.model.VersionInterface;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table v_employee
 */
@Data
@SuperBuilder
@EqualsAndHashCode
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class VEmployee implements Serializable, VersionInterface, StatusInterface {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column v_employee.emp_id
     *
     * @mbg.generated
     */
    private String empId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column v_employee.version
     *
     * @mbg.generated
     */
    @EqualsAndHashCode.Exclude
    private Long version;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column v_employee.status
     *
     * @mbg.generated
     */
    private String status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column v_employee.created_by
     *
     * @mbg.generated
     */
    @EqualsAndHashCode.Exclude
    private String createdBy;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column v_employee.created_date
     *
     * @mbg.generated
     */
    @EqualsAndHashCode.Exclude
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime createdDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column v_employee.last_modified_by
     *
     * @mbg.generated
     */
    @EqualsAndHashCode.Exclude
    private String lastModifiedBy;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column v_employee.last_modified_date
     *
     * @mbg.generated
     */
    @EqualsAndHashCode.Exclude
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime lastModifiedDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column v_employee.emp_name
     *
     * @mbg.generated
     */
    private String empName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column v_employee.password
     *
     * @mbg.generated
     */
    private String password;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column v_employee.email
     *
     * @mbg.generated
     */
    private String email;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column v_employee.organization_id
     *
     * @mbg.generated
     */
    private Long organizationId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column v_employee.group_name
     *
     * @mbg.generated
     */
    private String groupName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column v_employee.director_id
     *
     * @mbg.generated
     */
    private Long directorId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column v_employee.parent_id
     *
     * @mbg.generated
     */
    private Long parentId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column v_employee.position_id
     *
     * @mbg.generated
     */
    private Long positionId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column v_employee.position_name
     *
     * @mbg.generated
     */
    private String positionName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table v_employee
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;
}