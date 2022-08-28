package jp.co.stnet.cms.base.infrastructure.mapper.mbg;

import java.util.List;
import jp.co.stnet.cms.base.domain.model.mbg.MailSendHistory;
import jp.co.stnet.cms.base.domain.model.mbg.MailSendHistoryExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

@Mapper
public interface MailSendHistoryMapper extends jp.co.stnet.cms.base.infrastructure.mapper.MapperInterface<MailSendHistory, MailSendHistoryExample, Long> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mail_send_history
     *
     * @mbg.generated
     */
    @Override
    long countByExample(MailSendHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mail_send_history
     *
     * @mbg.generated
     */
    @Override
    int deleteByExample(MailSendHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mail_send_history
     *
     * @mbg.generated
     */
    int deleteByPrimaryKeyAndVersion(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mail_send_history
     *
     * @mbg.generated
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mail_send_history
     *
     * @mbg.generated
     */
    @Override
    int insert(MailSendHistory row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mail_send_history
     *
     * @mbg.generated
     */
    @Override
    int insertSelective(MailSendHistory row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mail_send_history
     *
     * @mbg.generated
     */
    List<MailSendHistory> selectByExampleWithRowbounds(MailSendHistoryExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mail_send_history
     *
     * @mbg.generated
     */
    @Override
    List<MailSendHistory> selectByExample(MailSendHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mail_send_history
     *
     * @mbg.generated
     */
    @Override
    MailSendHistory selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mail_send_history
     *
     * @mbg.generated
     */
    @Override
    int updateByExampleSelective(@Param("row") MailSendHistory row, @Param("example") MailSendHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mail_send_history
     *
     * @mbg.generated
     */
    @Override
    int updateByExample(@Param("row") MailSendHistory row, @Param("example") MailSendHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mail_send_history
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyAndVersionSelective(MailSendHistory row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mail_send_history
     *
     * @mbg.generated
     */
    @Override
    int updateByPrimaryKeySelective(MailSendHistory row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mail_send_history
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyAndVersion(MailSendHistory row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mail_send_history
     *
     * @mbg.generated
     */
    @Override
    int updateByPrimaryKey(MailSendHistory row);
}