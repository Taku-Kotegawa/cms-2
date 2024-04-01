package jp.co.stnet.cms.base.infrastructure.mapper.mbg;

import java.util.List;
import jp.co.stnet.cms.base.domain.model.mbg.FailedPasswordReissue;
import jp.co.stnet.cms.base.domain.model.mbg.FailedPasswordReissueExample;
import jp.co.stnet.cms.base.domain.model.mbg.FailedPasswordReissueKey;
import jp.co.stnet.cms.base.infrastructure.mapper.MapperInterface;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

@Mapper
public interface FailedPasswordReissueMapper extends MapperInterface<FailedPasswordReissue, FailedPasswordReissueExample, FailedPasswordReissueKey> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_failed_password_reissue
     *
     * @mbg.generated
     */
    long countByExample(FailedPasswordReissueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_failed_password_reissue
     *
     * @mbg.generated
     */
    int deleteByExample(FailedPasswordReissueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_failed_password_reissue
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(FailedPasswordReissueKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_failed_password_reissue
     *
     * @mbg.generated
     */
    int insert(FailedPasswordReissue row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_failed_password_reissue
     *
     * @mbg.generated
     */
    int insertSelective(FailedPasswordReissue row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_failed_password_reissue
     *
     * @mbg.generated
     */
    List<FailedPasswordReissue> selectByExampleWithRowbounds(FailedPasswordReissueExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_failed_password_reissue
     *
     * @mbg.generated
     */
    List<FailedPasswordReissue> selectByExample(FailedPasswordReissueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_failed_password_reissue
     *
     * @mbg.generated
     */
    FailedPasswordReissue selectByPrimaryKey(FailedPasswordReissueKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_failed_password_reissue
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("row") FailedPasswordReissue row, @Param("example") FailedPasswordReissueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_failed_password_reissue
     *
     * @mbg.generated
     */
    int updateByExample(@Param("row") FailedPasswordReissue row, @Param("example") FailedPasswordReissueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_failed_password_reissue
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(FailedPasswordReissue row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_failed_password_reissue
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(FailedPasswordReissue row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_failed_password_reissue
     *
     * @mbg.generated
     */
    int merge(FailedPasswordReissue row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_failed_password_reissue
     *
     * @mbg.generated
     */
    void truncate();
}