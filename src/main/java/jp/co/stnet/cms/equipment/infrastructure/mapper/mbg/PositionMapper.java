package jp.co.stnet.cms.equipment.infrastructure.mapper.mbg;

import java.util.List;
import jp.co.stnet.cms.base.infrastructure.mapper.VersionMapperInterface;
import jp.co.stnet.cms.equipment.domain.model.mbg.Position;
import jp.co.stnet.cms.equipment.domain.model.mbg.PositionExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

@Mapper
public interface PositionMapper extends VersionMapperInterface<Position, PositionExample, Long> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table position
     *
     * @mbg.generated
     */
    long countByExample(PositionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table position
     *
     * @mbg.generated
     */
    int deleteByExample(PositionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table position
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long positionId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table position
     *
     * @mbg.generated
     */
    int insert(Position row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table position
     *
     * @mbg.generated
     */
    int insertSelective(Position row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table position
     *
     * @mbg.generated
     */
    List<Position> selectByExampleWithRowbounds(PositionExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table position
     *
     * @mbg.generated
     */
    List<Position> selectByExample(PositionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table position
     *
     * @mbg.generated
     */
    Position selectByPrimaryKey(Long positionId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table position
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("row") Position row, @Param("example") PositionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table position
     *
     * @mbg.generated
     */
    int updateByExample(@Param("row") Position row, @Param("example") PositionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table position
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyAndVersionSelective(Position row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table position
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Position row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table position
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyAndVersion(Position row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table position
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Position row);
}