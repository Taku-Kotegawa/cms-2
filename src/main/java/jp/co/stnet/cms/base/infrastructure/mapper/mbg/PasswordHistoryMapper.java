package jp.co.stnet.cms.base.infrastructure.mapper.mbg;

import java.util.List;
import jp.co.stnet.cms.base.domain.model.mbg.PasswordHistory;
import jp.co.stnet.cms.base.domain.model.mbg.PasswordHistoryExample;
import jp.co.stnet.cms.base.domain.model.mbg.PasswordHistoryKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

@Mapper
public interface PasswordHistoryMapper extends jp.co.stnet.cms.base.infrastructure.mapper.MapperInterface<PasswordHistory, PasswordHistoryExample, PasswordHistoryKey> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table password_history
     *
     * @mbg.generated
     */
    @Override
    long countByExample(PasswordHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table password_history
     *
     * @mbg.generated
     */
    @Override
    int deleteByExample(PasswordHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table password_history
     *
     * @mbg.generated
     */
    @Override
    int deleteByPrimaryKey(PasswordHistoryKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table password_history
     *
     * @mbg.generated
     */
    @Override
    int insert(PasswordHistory row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table password_history
     *
     * @mbg.generated
     */
    @Override
    int insertSelective(PasswordHistory row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table password_history
     *
     * @mbg.generated
     */
    List<PasswordHistory> selectByExampleWithRowbounds(PasswordHistoryExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table password_history
     *
     * @mbg.generated
     */
    @Override
    List<PasswordHistory> selectByExample(PasswordHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table password_history
     *
     * @mbg.generated
     */
    @Override
    PasswordHistory selectByPrimaryKey(PasswordHistoryKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table password_history
     *
     * @mbg.generated
     */
    @Override
    int updateByExampleSelective(@Param("row") PasswordHistory row, @Param("example") PasswordHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table password_history
     *
     * @mbg.generated
     */
    @Override
    int updateByExample(@Param("row") PasswordHistory row, @Param("example") PasswordHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table password_history
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyAndVersionSelective(PasswordHistory row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table password_history
     *
     * @mbg.generated
     */
    @Override
    int updateByPrimaryKeySelective(PasswordHistory row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table password_history
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyAndVersion(PasswordHistory row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table password_history
     *
     * @mbg.generated
     */
    @Override
    int updateByPrimaryKey(PasswordHistory row);
}