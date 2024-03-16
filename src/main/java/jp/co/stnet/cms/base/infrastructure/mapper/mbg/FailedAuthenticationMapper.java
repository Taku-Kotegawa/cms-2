package jp.co.stnet.cms.base.infrastructure.mapper.mbg;

import java.util.List;
import jp.co.stnet.cms.base.domain.model.mbg.FailedAuthentication;
import jp.co.stnet.cms.base.domain.model.mbg.FailedAuthenticationExample;
import jp.co.stnet.cms.base.domain.model.mbg.FailedAuthenticationKey;
import jp.co.stnet.cms.base.infrastructure.mapper.MapperInterface;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

@Mapper
public interface FailedAuthenticationMapper extends MapperInterface<FailedAuthentication, FailedAuthenticationExample, FailedAuthenticationKey> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_failed_authentication
     *
     * @mbg.generated
     */
    long countByExample(FailedAuthenticationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_failed_authentication
     *
     * @mbg.generated
     */
    int deleteByExample(FailedAuthenticationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_failed_authentication
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(FailedAuthenticationKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_failed_authentication
     *
     * @mbg.generated
     */
    int insert(FailedAuthentication row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_failed_authentication
     *
     * @mbg.generated
     */
    int insertSelective(FailedAuthentication row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_failed_authentication
     *
     * @mbg.generated
     */
    List<FailedAuthentication> selectByExampleWithRowbounds(FailedAuthenticationExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_failed_authentication
     *
     * @mbg.generated
     */
    List<FailedAuthentication> selectByExample(FailedAuthenticationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_failed_authentication
     *
     * @mbg.generated
     */
    FailedAuthentication selectByPrimaryKey(FailedAuthenticationKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_failed_authentication
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("row") FailedAuthentication row, @Param("example") FailedAuthenticationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_failed_authentication
     *
     * @mbg.generated
     */
    int updateByExample(@Param("row") FailedAuthentication row, @Param("example") FailedAuthenticationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_failed_authentication
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyAndVersionSelective(FailedAuthentication row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_failed_authentication
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(FailedAuthentication row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_failed_authentication
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyAndVersion(FailedAuthentication row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_failed_authentication
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(FailedAuthentication row);
}