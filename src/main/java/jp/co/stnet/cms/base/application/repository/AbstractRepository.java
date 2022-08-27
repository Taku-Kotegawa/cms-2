package jp.co.stnet.cms.base.application.repository;

import jp.co.stnet.cms.base.domain.model.KeyInterface;
import jp.co.stnet.cms.base.infrastructure.mapper.MapperInterface;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public abstract class AbstractRepository<T extends KeyInterface<I>, E, I> implements MapperInterface<T, E, I> {

    abstract MapperInterface<T, E, I> mapper();

    @Override
    public long countByExample(E example) {
        return mapper().countByExample(example);
    }

    @Override
    public int deleteByExample(E example) {
        return mapper().deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(I id) {
        return mapper().deleteByPrimaryKey(id);
    }

    @Override
    public int insert(T row) {
        return mapper().insert(row);
    }

    @Override
    public int insertSelective(T row) {
        return mapper().insertSelective(row);
    }

    @Override
    public List<T> selectByExampleWithRowbounds(E example, RowBounds rowBounds) {
        return mapper().selectByExampleWithRowbounds(example, rowBounds);
    }

    @Override
    public List<T> selectByExample(E example) {
        return mapper().selectByExample(example);
    }

    @Override
    public T selectByPrimaryKey(I id) {
        return mapper().selectByPrimaryKey(id);
    }

    @Override
    public int updateByExampleSelective(T row, E example) {
        return mapper().updateByExampleSelective(row, example);
    }

    @Override
    public int updateByExample(T row, E example) {
        return mapper().updateByExample(row, example);
    }

    @Override
    public int updateByPrimaryKeySelective(T row) {
        return mapper().updateByPrimaryKeySelective(row);
    }

    @Override
    public int updateByPrimaryKey(T row) {
        return mapper().updateByPrimaryKey(row);
    }
}
