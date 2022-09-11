package jp.co.stnet.cms.base.application.repository.interfaces;

import jp.co.stnet.cms.base.domain.model.KeyInterface;
import org.apache.ibatis.session.RowBounds;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface RepositoryInterface<T extends KeyInterface<I>, E, I> {

    Optional<T> findById(I id);

    T getOne(I id);

    boolean existsById(I id);

    List<T> findAll();

    List<T> findAllById(List<I> ids);

    List<T> findAllByExample(E example);

    Page<T> findAllByExampleWithRowBounds(E example, RowBounds rowBounds);

    T register(T entity);

    List<T> registerAll(List<T> entities);

    T save(T entity);

    List<T> saveAll(List<T> entities);

    void deleteById(I id);

    void deleteAll();

    void deleteAll(List<T> entities);

    void deleteByExample(E example);

}
