package jp.co.stnet.cms.base.domain.model.mbg;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.LocalDateTime;
import jp.co.stnet.cms.base.domain.model.KeyInterface;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Database Table Remarks:
 *   ロール・パーミッション
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_temp_file
 */
@Data
@SuperBuilder
@EqualsAndHashCode
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class TempFile implements Serializable, KeyInterface<String> {
    /**
     * Database Column Remarks:
     *   id
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_temp_file.id
     *
     * @mbg.generated
     */
    private String id;

    /**
     * Database Column Remarks:
     *   件名
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_temp_file.body
     *
     * @mbg.generated
     */
    private Long body;

    /**
     * Database Column Remarks:
     *   ファイル名
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_temp_file.original_name
     *
     * @mbg.generated
     */
    private String originalName;

    /**
     * Database Column Remarks:
     *   作成者
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_temp_file.created_by
     *
     * @mbg.generated
     */
    private String createdBy;

    /**
     * Database Column Remarks:
     *   作成日時
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_temp_file.created_date
     *
     * @mbg.generated
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime createdDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_temp_file
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }
}