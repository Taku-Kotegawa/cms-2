package jp.co.stnet.cms.example.domain.model.mbg;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table simple_entity_combobox03
 */
@Data
@SuperBuilder
@EqualsAndHashCode
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class SimpleEntityCombobox03 implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column simple_entity_combobox03.simple_entity_id
     *
     * @mbg.generated
     */
    private Long simpleEntityId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column simple_entity_combobox03.combobox03
     *
     * @mbg.generated
     */
    private String combobox03;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table simple_entity_combobox03
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;
}