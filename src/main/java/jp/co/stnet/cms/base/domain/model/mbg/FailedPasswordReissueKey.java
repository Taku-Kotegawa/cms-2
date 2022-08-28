package jp.co.stnet.cms.base.domain.model.mbg;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * Database Table Remarks:
 *   パスワード再発行失敗履歴
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table failed_password_reissue
 */
@Data
public class FailedPasswordReissueKey implements Serializable {
    /**
     * Database Column Remarks:
     *   トークン
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column failed_password_reissue.token
     *
     * @mbg.generated
     */
    private String token;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column failed_password_reissue.attempt_date
     *
     * @mbg.generated
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime attemptDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table failed_password_reissue
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;
}