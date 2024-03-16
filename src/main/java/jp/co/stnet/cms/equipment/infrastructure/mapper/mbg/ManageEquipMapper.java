package jp.co.stnet.cms.equipment.infrastructure.mapper.mbg;

import java.util.List;
import jp.co.stnet.cms.base.infrastructure.mapper.VersionMapperInterface;
import jp.co.stnet.cms.equipment.domain.model.mbg.ManageEquip;
import jp.co.stnet.cms.equipment.domain.model.mbg.ManageEquipExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

@Mapper
public interface ManageEquipMapper extends VersionMapperInterface<ManageEquip, ManageEquipExample, Long> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table manage_equip
     *
     * @mbg.generated
     */
    long countByExample(ManageEquipExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table manage_equip
     *
     * @mbg.generated
     */
    int deleteByExample(ManageEquipExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table manage_equip
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long equipId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table manage_equip
     *
     * @mbg.generated
     */
    int insert(ManageEquip row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table manage_equip
     *
     * @mbg.generated
     */
    int insertSelective(ManageEquip row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table manage_equip
     *
     * @mbg.generated
     */
    List<ManageEquip> selectByExampleWithRowbounds(ManageEquipExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table manage_equip
     *
     * @mbg.generated
     */
    List<ManageEquip> selectByExample(ManageEquipExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table manage_equip
     *
     * @mbg.generated
     */
    ManageEquip selectByPrimaryKey(Long equipId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table manage_equip
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("row") ManageEquip row, @Param("example") ManageEquipExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table manage_equip
     *
     * @mbg.generated
     */
    int updateByExample(@Param("row") ManageEquip row, @Param("example") ManageEquipExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table manage_equip
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyAndVersionSelective(ManageEquip row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table manage_equip
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(ManageEquip row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table manage_equip
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyAndVersion(ManageEquip row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table manage_equip
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(ManageEquip row);
}