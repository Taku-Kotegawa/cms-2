package jp.co.stnet.cms.base.application.repository;

import jp.co.stnet.cms.base.application.repository.interfaces.RepositoryInterface;
import jp.co.stnet.cms.base.domain.model.KeyInterface;
import jp.co.stnet.cms.base.infrastructure.mapper.MapperInterface;
import org.apache.ibatis.session.RowBounds;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class AbstractRepository<T extends KeyInterface<I>, E, I> implements RepositoryInterface<T, E, I> {

    abstract <S extends T> MapperInterface<T, E, I> mapper();

    abstract E example();

    @Override
    public T register(T entity) {
        Objects.requireNonNull(entity);
        mapper().insert(entity);
        return getOne(entity.getId());
    }

    @Override
    public List<T> registerAll(List<T> entities) {
        Objects.requireNonNull(entities);
        var result = new ArrayList<T>();
        for (var entity : entities) {
            result.add(register(entity));
        }
        return result;
    }

    @Override
    public T save(T entity) {
        Objects.requireNonNull(entity);
        if (!existsById(entity.getId())) {
            return register(entity);
        }
        mapper().updateByPrimaryKey(entity);
        return getOne(entity.getId());
    }

    @Override
    public List<T> saveAll(List<T> entities) {
        Objects.requireNonNull(entities);
        var result = new ArrayList<T>();
        for (var entity : entities) {
            result.add(save(entity));
        }
        return result;
    }

    @Override
    public Optional<T> findById(I id) {
        Objects.requireNonNull(id);
        return Optional.ofNullable(mapper().selectByPrimaryKey(id));
    }

    public T getOne(I id) {
        Objects.requireNonNull(id);
        return findById(id).orElseThrow(() -> new ResourceNotFoundException("id = " + id));
    }

    @Override
    public boolean existsById(I id) {
        if (id == null) {
            return false;
        }
        return mapper().selectByPrimaryKey(id) != null;
    }

    @Override
    public List<T> findAllById(List<I> ids) {
        Objects.requireNonNull(ids);
        var result = new ArrayList<T>();
        for (I id : ids) {
            var entity = findById(id);
            entity.ifPresent(result::add);
        }
        return result;
    }

    @Override
    public List<T> findAll() {
        return mapper().selectByExample(example());
    }

    @Override
    public void delete(T entity) {
        Objects.requireNonNull(entity);
        deleteById(entity.getId());
    }

    @Override
    public void deleteById(I id) {
        Objects.requireNonNull(id);
        mapper().deleteByPrimaryKey(id);
    }

    @Override
    public void deleteAll(List<T> entities) {
        Objects.requireNonNull(entities);
        entities.forEach(x -> delete(x));
    }

    @Override
    public void deleteAll() {
        mapper().deleteByExample(example());
    }

    @Override
    public void deleteByExample(E example) {
        Objects.requireNonNull(example);
        mapper().deleteByExample(example);
    }

    @Override
    public void deleteAllById(List<I> ids) {
        Objects.requireNonNull(ids);
        ids.forEach(this::deleteById);
    }

    public List<T> findAllByExample(E example) {
        return mapper().selectByExample(example);
    }

    @Override
    public Page<T> findAllByExampleWithRowBounds(E example, RowBounds rowBounds) {
        var result = mapper().selectByExampleWithRowbounds(example, rowBounds);
        Pageable pageable = PageRequest.of(rowBounds.getOffset(), rowBounds.getLimit());
        return new PageImpl<>(
                result,
                pageable,
                result.size()
        );
    }

}
