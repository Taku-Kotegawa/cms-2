package jp.co.stnet.cms.base.infrastructure.mapper;

import jp.co.stnet.cms.base.domain.model.KeyInterface;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * MyBatisGeneratorで生成されるMapper Interface共通のメソッドを定義する
 *
 * @param <T> Modelクラス　ex) Account
 */
public interface MapperInterface<T extends KeyInterface<ID>, E, ID> {

    long countByExample(E example);

    int deleteByExample(E example);

    int deleteByPrimaryKey(ID id);

    int insert(T row);

    int insertSelective(T row);

    List<T> selectByExampleWithRowbounds(E example, RowBounds rowBounds);

    List<T> selectByExample(E example);

    T selectByPrimaryKey(ID id);

    int updateByExampleSelective(@Param("row") T row, @Param("example") E example);

    int updateByExample(@Param("row") T row, @Param("example") E example);

    int updateByPrimaryKeySelective(T row);

    int updateByPrimaryKey(T row);
}
