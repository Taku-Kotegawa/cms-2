package jp.co.stnet.cms.example.infrastructure.mapper.mbg;

import java.util.List;
import jp.co.stnet.cms.example.domain.model.mbg.SimpleEntitySelect04;
import jp.co.stnet.cms.example.domain.model.mbg.SimpleEntitySelect04Example;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

@Mapper
public interface SimpleEntitySelect04Mapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table simple_entity_select04
     *
     * @mbg.generated
     */
    long countByExample(SimpleEntitySelect04Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table simple_entity_select04
     *
     * @mbg.generated
     */
    int deleteByExample(SimpleEntitySelect04Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table simple_entity_select04
     *
     * @mbg.generated
     */
    int insert(SimpleEntitySelect04 row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table simple_entity_select04
     *
     * @mbg.generated
     */
    int insertSelective(SimpleEntitySelect04 row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table simple_entity_select04
     *
     * @mbg.generated
     */
    List<SimpleEntitySelect04> selectByExampleWithRowbounds(SimpleEntitySelect04Example example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table simple_entity_select04
     *
     * @mbg.generated
     */
    List<SimpleEntitySelect04> selectByExample(SimpleEntitySelect04Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table simple_entity_select04
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("row") SimpleEntitySelect04 row, @Param("example") SimpleEntitySelect04Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table simple_entity_select04
     *
     * @mbg.generated
     */
    int updateByExample(@Param("row") SimpleEntitySelect04 row, @Param("example") SimpleEntitySelect04Example example);
}