package jp.co.stnet.cms.base.infrastructure.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * MyBatisGeneratorで生成されるMapper Interface共通のメソッドを定義する
 * @param <T> Modelクラス　ex) Account
 * @param <U> Exampleクラス ex) AccountExample
 * @param <ID> 主キーのデータ型クラス or 主キークラス ex) AccountKey
 */
public interface MapperInterface<T, U, ID> {

    long countByExample(U example);

    int deleteByExample(U example);

    int deleteByPrimaryKey(ID id);

    int insert(T row);

    int insertSelective(T row);

    List<T> selectByExampleWithRowbounds(U example, RowBounds rowBounds);

    List<T> selectByExample(U example);

    T selectByPrimaryKey(ID id);

    int updateByExampleSelective(@Param("row") T row, @Param("example") U example);

    int updateByExample(@Param("row") T row, @Param("example") U example);

    int updateByPrimaryKeyAndVersionSelective(T row);

    int updateByPrimaryKeySelective(T row);

    int updateByPrimaryKeyAndVersion(T row);

    int updateByPrimaryKey(T row);

}
