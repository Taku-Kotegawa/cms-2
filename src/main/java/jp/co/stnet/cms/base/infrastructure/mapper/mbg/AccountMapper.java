package jp.co.stnet.cms.base.infrastructure.mapper.mbg;

import java.util.List;
import jp.co.stnet.cms.base.domain.model.mbg.Account;
import jp.co.stnet.cms.base.domain.model.mbg.AccountExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

@Mapper
public interface AccountMapper extends jp.co.stnet.cms.base.infrastructure.mapper.VersionMapperInterface<Account, AccountExample, String> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account
     *
     * @mbg.generated
     */
    @Override
    long countByExample(AccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account
     *
     * @mbg.generated
     */
    @Override
    int deleteByExample(AccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account
     *
     * @mbg.generated
     */
    int deleteByPrimaryKeyAndVersion(String username);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account
     *
     * @mbg.generated
     */
    @Override
    int deleteByPrimaryKey(String username);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account
     *
     * @mbg.generated
     */
    @Override
    int insert(Account row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account
     *
     * @mbg.generated
     */
    @Override
    int insertSelective(Account row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account
     *
     * @mbg.generated
     */
    List<Account> selectByExampleWithRowbounds(AccountExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account
     *
     * @mbg.generated
     */
    @Override
    List<Account> selectByExample(AccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account
     *
     * @mbg.generated
     */
    @Override
    Account selectByPrimaryKey(String username);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account
     *
     * @mbg.generated
     */
    @Override
    int updateByExampleSelective(@Param("row") Account row, @Param("example") AccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account
     *
     * @mbg.generated
     */
    @Override
    int updateByExample(@Param("row") Account row, @Param("example") AccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyAndVersionSelective(Account row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account
     *
     * @mbg.generated
     */
    @Override
    int updateByPrimaryKeySelective(Account row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyAndVersion(Account row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account
     *
     * @mbg.generated
     */
    @Override
    int updateByPrimaryKey(Account row);
}