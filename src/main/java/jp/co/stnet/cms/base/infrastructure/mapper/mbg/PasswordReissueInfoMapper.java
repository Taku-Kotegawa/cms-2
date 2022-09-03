package jp.co.stnet.cms.base.infrastructure.mapper.mbg;

import java.util.List;
import jp.co.stnet.cms.base.domain.model.mbg.PasswordReissueInfo;
import jp.co.stnet.cms.base.domain.model.mbg.PasswordReissueInfoExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

@Mapper
public interface PasswordReissueInfoMapper extends jp.co.stnet.cms.base.infrastructure.mapper.MapperInterface<PasswordReissueInfo, PasswordReissueInfoExample, String> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table password_reissue_info
     *
     * @mbg.generated
     */
    @Override
    long countByExample(PasswordReissueInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table password_reissue_info
     *
     * @mbg.generated
     */
    @Override
    int deleteByExample(PasswordReissueInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table password_reissue_info
     *
     * @mbg.generated
     */
    @Override
    int deleteByPrimaryKey(String token);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table password_reissue_info
     *
     * @mbg.generated
     */
    @Override
    int insert(PasswordReissueInfo row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table password_reissue_info
     *
     * @mbg.generated
     */
    @Override
    int insertSelective(PasswordReissueInfo row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table password_reissue_info
     *
     * @mbg.generated
     */
    List<PasswordReissueInfo> selectByExampleWithRowbounds(PasswordReissueInfoExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table password_reissue_info
     *
     * @mbg.generated
     */
    @Override
    List<PasswordReissueInfo> selectByExample(PasswordReissueInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table password_reissue_info
     *
     * @mbg.generated
     */
    @Override
    PasswordReissueInfo selectByPrimaryKey(String token);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table password_reissue_info
     *
     * @mbg.generated
     */
    @Override
    int updateByExampleSelective(@Param("row") PasswordReissueInfo row, @Param("example") PasswordReissueInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table password_reissue_info
     *
     * @mbg.generated
     */
    @Override
    int updateByExample(@Param("row") PasswordReissueInfo row, @Param("example") PasswordReissueInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table password_reissue_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyAndVersionSelective(PasswordReissueInfo row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table password_reissue_info
     *
     * @mbg.generated
     */
    @Override
    int updateByPrimaryKeySelective(PasswordReissueInfo row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table password_reissue_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyAndVersion(PasswordReissueInfo row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table password_reissue_info
     *
     * @mbg.generated
     */
    @Override
    int updateByPrimaryKey(PasswordReissueInfo row);
}