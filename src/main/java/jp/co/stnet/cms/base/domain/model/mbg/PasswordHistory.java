package jp.co.stnet.cms.base.domain.model.mbg;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import jp.co.stnet.cms.base.domain.model.KeyInterface;
import jp.co.stnet.cms.base.domain.model.mbg.PasswordHistoryKey;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Database Table Remarks:
 *   パスワード履歴
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table password_history
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PasswordHistory extends PasswordHistoryKey implements Serializable, KeyInterface<PasswordHistoryKey> {
    /**
     * Database Column Remarks:
     *   パスワード
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column password_history.password
     *
     * @mbg.generated
     */
    private String password;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table password_history
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    public PasswordHistoryKey getId() {
        PasswordHistoryKey superClass = new PasswordHistoryKey();
        superClass.setUsername(getUsername());
        superClass.setUseFrom(getUseFrom());
        return superClass;
    }
}