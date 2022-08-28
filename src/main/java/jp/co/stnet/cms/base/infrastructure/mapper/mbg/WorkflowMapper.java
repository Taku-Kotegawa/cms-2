package jp.co.stnet.cms.base.infrastructure.mapper.mbg;

import java.util.List;
import jp.co.stnet.cms.base.domain.model.mbg.Workflow;
import jp.co.stnet.cms.base.domain.model.mbg.WorkflowExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

@Mapper
public interface WorkflowMapper extends jp.co.stnet.cms.base.infrastructure.mapper.VersionMapperInterface<Workflow, WorkflowExample, Long> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table workflow
     *
     * @mbg.generated
     */
    @Override
    long countByExample(WorkflowExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table workflow
     *
     * @mbg.generated
     */
    @Override
    int deleteByExample(WorkflowExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table workflow
     *
     * @mbg.generated
     */
    int deleteByPrimaryKeyAndVersion(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table workflow
     *
     * @mbg.generated
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table workflow
     *
     * @mbg.generated
     */
    @Override
    int insert(Workflow row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table workflow
     *
     * @mbg.generated
     */
    @Override
    int insertSelective(Workflow row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table workflow
     *
     * @mbg.generated
     */
    List<Workflow> selectByExampleWithRowbounds(WorkflowExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table workflow
     *
     * @mbg.generated
     */
    @Override
    List<Workflow> selectByExample(WorkflowExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table workflow
     *
     * @mbg.generated
     */
    @Override
    Workflow selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table workflow
     *
     * @mbg.generated
     */
    @Override
    int updateByExampleSelective(@Param("row") Workflow row, @Param("example") WorkflowExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table workflow
     *
     * @mbg.generated
     */
    @Override
    int updateByExample(@Param("row") Workflow row, @Param("example") WorkflowExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table workflow
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyAndVersionSelective(Workflow row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table workflow
     *
     * @mbg.generated
     */
    @Override
    int updateByPrimaryKeySelective(Workflow row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table workflow
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyAndVersion(Workflow row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table workflow
     *
     * @mbg.generated
     */
    @Override
    int updateByPrimaryKey(Workflow row);
}