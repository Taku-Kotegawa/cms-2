package jp.co.stnet.cms.base.application.service;


import jp.co.stnet.cms.base.application.repository.interfaces.VersionRepositoryInterface;
import jp.co.stnet.cms.base.domain.enums.Status;
import jp.co.stnet.cms.base.domain.model.KeyInterface;
import jp.co.stnet.cms.base.domain.model.StatusInterface;
import jp.co.stnet.cms.base.domain.model.VersionInterface;
import jp.co.stnet.cms.base.infrastructure.mapper.ViewMapperInterface;
import jp.co.stnet.cms.common.exception.DataIntegrityViolationBusinessException;
import jp.co.stnet.cms.common.exception.IllegalStateBusinessException;
import jp.co.stnet.cms.common.exception.OptimisticLockingFailureBusinessException;
import jp.co.stnet.cms.common.message.MessageKeys;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.terasoluna.gfw.common.message.ResultMessages;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public abstract class AbstractVNodeService<
        T extends KeyInterface<I> & VersionInterface & StatusInterface,
        V extends KeyInterface<I> & VersionInterface & StatusInterface, E, I> implements VNodeIService<T, V, E, I> {
    abstract protected VersionRepositoryInterface<T, E, I> repository();

    abstract protected ViewMapperInterface<V, E> viewMapper();

    @Override
    abstract public V findById(I id);

    /**
     * 保存前処理(Override用)
     *
     * @param entity  更新内容のエンティティ
     * @param current 更新前のエンティティ
     */
    protected void beforeSave(T entity, T current) {
    }

    @Override
    public T save(T entity) {
        try {
            if (!repository().existsById(entity.getId())) {
                return repository().save(entity);
            }

            var current = repository().getOne(entity.getId());
            beforeSave(entity, current);
            repository().save(entity);
            afterSave(entity, current);

        } catch (OptimisticLockingFailureException e) {
            throw new OptimisticLockingFailureBusinessException(ResultMessages.error().add(MessageKeys.E_CM_FW_8001));
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationBusinessException(ResultMessages.error().add(MessageKeys.E_CM_FW_8002, e.getMessage()));
        }
        return repository().getOne(entity.getId());
    }

    /**
     * 保存後処理(Override用)
     *
     * @param entity  更新内容のエンティティ
     * @param current 更新前のエンティティ
     */
    protected void afterSave(T entity, T current) {
    }

    @Override
    public boolean equalsEntity(T entity, T other) {
        return Objects.equals(entity, other);
    }

    @Override
    public Iterable<T> save(Iterable<T> entities) {
        List<T> saved = new ArrayList<>();
        for (T entity : entities) {
            saved.add(save(entity));
        }
        return saved;
    }

    @Override
    public T invalid(I id) {
        T entity = repository().getOne(id);
        // 楽観的排他制御の代わりに現在のステータスをチェック
        if (Status.INVALID.getCodeValue().equals(entity.getStatus())) {
            throw new IllegalStateBusinessException(ResultMessages.warning().add((MessageKeys.W_CM_FW_2003)));
        }
        entity.setStatus(Status.INVALID.getCodeValue());
        return repository().save(entity);
    }

    @Override
    public Iterable<T> invalid(Iterable<I> ids) {
        List<T> saves = new ArrayList<>();
        for (I id : ids) {
            T entity = repository().getOne(id);
            // ステータスがVALID以外のデータは処理をスキップする
            if (entity.getStatus().equals(Status.VALID.getCodeValue())) {
                saves.add(invalid(id));
            }
        }
        return saves;
    }

    @Override
    public T valid(I id) {
        T entity = repository().getOne(id);
        // 楽観的排他制御の代わりに現在のステータスをチェック
        if (Status.VALID.getCodeValue().equals(entity.getStatus())) {
            throw new IllegalStateBusinessException(ResultMessages.warning().add((MessageKeys.W_CM_FW_2003)));
        }
        entity.setStatus(Status.VALID.getCodeValue());
        return repository().save(entity);
    }

    @Override
    public Iterable<T> valid(Iterable<I> ids) {
        List<T> saves = new ArrayList<>();
        for (I id : ids) {
            T entity = repository().getOne(id);
            // ステータスがINVALID以外のデータは処理をスキップする
            if (entity.getStatus().equals(Status.INVALID.getCodeValue())) {
                saves.add(valid(id));
            }
        }
        return saves;
    }

    @Override
    public void delete(I id) {
        repository().deleteById(id);
    }

    @Override
    public void delete(Iterable<T> entities) {
        repository().deleteAll((List<T>) entities);
    }

    @Override
    public Page<V> findPageByExampleWithRowBounds(E example, RowBounds rowBounds) {
        var result = viewMapper().selectByExampleWithRowbounds(example, rowBounds);
        Pageable pageable = PageRequest.of(rowBounds.getOffset(), rowBounds.getLimit());
        return new PageImpl<>(
                result,
                pageable,
                result.size()
        );
    }

}
