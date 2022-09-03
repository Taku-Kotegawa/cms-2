package jp.co.stnet.cms.base.infrastructure.mapper.mbg;

import java.util.List;
import jp.co.stnet.cms.base.domain.model.mbg.SuccessfulAuthentication;
import jp.co.stnet.cms.base.domain.model.mbg.SuccessfulAuthenticationExample;
import jp.co.stnet.cms.base.domain.model.mbg.SuccessfulAuthenticationKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

@Mapper
public interface SuccessfulAuthenticationMapper extends jp.co.stnet.cms.base.infrastructure.mapper.MapperInterface<SuccessfulAuthentication, SuccessfulAuthenticationExample, SuccessfulAuthenticationKey> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table successful_authentication
     *
     * @mbg.generated
     */
    @Override
    long countByExample(SuccessfulAuthenticationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table successful_authentication
     *
     * @mbg.generated
     */
    @Override
    int deleteByExample(SuccessfulAuthenticationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table successful_authentication
     *
     * @mbg.generated
     */
    @Override
    int deleteByPrimaryKey(SuccessfulAuthenticationKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table successful_authentication
     *
     * @mbg.generated
     */
    @Override
    int insert(SuccessfulAuthentication row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table successful_authentication
     *
     * @mbg.generated
     */
    @Override
    int insertSelective(SuccessfulAuthentication row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table successful_authentication
     *
     * @mbg.generated
     */
    List<SuccessfulAuthentication> selectByExampleWithRowbounds(SuccessfulAuthenticationExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table successful_authentication
     *
     * @mbg.generated
     */
    @Override
    List<SuccessfulAuthentication> selectByExample(SuccessfulAuthenticationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table successful_authentication
     *
     * @mbg.generated
     */
    @Override
    SuccessfulAuthentication selectByPrimaryKey(SuccessfulAuthenticationKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table successful_authentication
     *
     * @mbg.generated
     */
    @Override
    int updateByExampleSelective(@Param("row") SuccessfulAuthentication row, @Param("example") SuccessfulAuthenticationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table successful_authentication
     *
     * @mbg.generated
     */
    @Override
    int updateByExample(@Param("row") SuccessfulAuthentication row, @Param("example") SuccessfulAuthenticationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table successful_authentication
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyAndVersionSelective(SuccessfulAuthentication row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table successful_authentication
     *
     * @mbg.generated
     */
    @Override
    int updateByPrimaryKeySelective(SuccessfulAuthentication row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table successful_authentication
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyAndVersion(SuccessfulAuthentication row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table successful_authentication
     *
     * @mbg.generated
     */
    @Override
    int updateByPrimaryKey(SuccessfulAuthentication row);
}