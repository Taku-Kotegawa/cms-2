package jp.co.stnet.cms.base.domain.model.mbg;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Database Table Remarks:
 *   ユーザ・ロール関連
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_role
 */
@Data
@SuperBuilder
@EqualsAndHashCode
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class TRoleKey implements Serializable {
    /**
     * Database Column Remarks:
     *   ユーザid
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_role.username
     *
     * @mbg.generated
     */
    private String username;

    /**
     * Database Column Remarks:
     *   ロール
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_role.role
     *
     * @mbg.generated
     */
    private String role;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_role
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;
}