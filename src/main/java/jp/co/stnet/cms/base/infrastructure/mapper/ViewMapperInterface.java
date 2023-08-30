package jp.co.stnet.cms.base.infrastructure.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * MyBatisGeneratorで生成されるMapper Interface共通のメソッドを定義する
 *
 * @param <T> Modelクラス　ex) Account
 */
public interface ViewMapperInterface<T, E> {

    long countByExample(E example);

    List<T> selectByExampleWithRowbounds(E example, RowBounds rowBounds);

    List<T> selectByExample(E example);

}
