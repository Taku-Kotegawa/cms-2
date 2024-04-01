package jp.co.stnet.cms.example.domain.model.mbg;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table simple_entity_checkbox02
 */
@Data
@SuperBuilder
@EqualsAndHashCode
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class SimpleEntityCheckbox02 {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column simple_entity_checkbox02.simple_entity_id
     *
     * @mbg.generated
     */
    private Long simpleEntityId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column simple_entity_checkbox02.checkbox02
     *
     * @mbg.generated
     */
    private String checkbox02;
}