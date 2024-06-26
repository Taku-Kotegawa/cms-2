package jp.co.stnet.cms.base.application.repository.interfaces;

import jp.co.stnet.cms.base.domain.model.KeyInterface;
import org.apache.ibatis.session.RowBounds;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ComplexRepositoryInterface<T extends KeyInterface<I>, E, I, S extends T> {

    /**
     * @param id
     * @return
     */
    Optional<S> findById(I id);

    S getOne(I id);

    boolean existsById(I id);

    List<S> findAll();

    List<S> findAllById(List<I> ids);

    List<S> findAllByExample(E example);

    Page<S> findAllByExampleWithRowBounds(E example, RowBounds rowBounds);

    S register(S entity);

    List<S> registerAll(List<S> entities);

    S save(S entity);

    List<S> saveAll(List<S> entities);

    void delete(S entity);

    void deleteById(I id);

    void deleteAll();

    void deleteAll(List<S> entities);

    void deleteAllById(List<I> ids);

    void deleteByExample(E example);

}
