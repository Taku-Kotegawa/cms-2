package jp.co.stnet.cms.example.infrastructure.mapper.mbg;

import java.util.List;
import jp.co.stnet.cms.example.domain.model.mbg.SimpleEntityCheckbox02;
import jp.co.stnet.cms.example.domain.model.mbg.SimpleEntityCheckbox02Example;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

@Mapper
public interface SimpleEntityCheckbox02Mapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table simple_entity_checkbox02
     *
     * @mbg.generated
     */
    long countByExample(SimpleEntityCheckbox02Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table simple_entity_checkbox02
     *
     * @mbg.generated
     */
    int deleteByExample(SimpleEntityCheckbox02Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table simple_entity_checkbox02
     *
     * @mbg.generated
     */
    int insert(SimpleEntityCheckbox02 row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table simple_entity_checkbox02
     *
     * @mbg.generated
     */
    int insertSelective(SimpleEntityCheckbox02 row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table simple_entity_checkbox02
     *
     * @mbg.generated
     */
    List<SimpleEntityCheckbox02> selectByExampleWithRowbounds(SimpleEntityCheckbox02Example example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table simple_entity_checkbox02
     *
     * @mbg.generated
     */
    List<SimpleEntityCheckbox02> selectByExample(SimpleEntityCheckbox02Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table simple_entity_checkbox02
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("row") SimpleEntityCheckbox02 row, @Param("example") SimpleEntityCheckbox02Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table simple_entity_checkbox02
     *
     * @mbg.generated
     */
    int updateByExample(@Param("row") SimpleEntityCheckbox02 row, @Param("example") SimpleEntityCheckbox02Example example);
}