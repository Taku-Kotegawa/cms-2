package jp.co.stnet.cms.base.application.service;


import jp.co.stnet.cms.base.application.repository.interfaces.RepositoryInterface;
import jp.co.stnet.cms.base.domain.enums.Status;
import jp.co.stnet.cms.base.domain.model.KeyInterface;
import jp.co.stnet.cms.base.domain.model.StatusInterface;
import jp.co.stnet.cms.base.domain.model.VersionInterface;
import jp.co.stnet.cms.base.infrastructure.mapper.VersionMapperInterface;
import jp.co.stnet.cms.common.exception.OptimisticLockingFailureBusinessException;
import jp.co.stnet.cms.common.message.MessageKeys;
import lombok.extern.slf4j.Slf4j;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;
import org.terasoluna.gfw.common.message.ResultMessages;

import java.util.List;
import java.util.Optional;

@Slf4j
public abstract class AbstractNodeService<T extends KeyInterface<I> & VersionInterface & StatusInterface, E, I> implements NodeIService<T, E, I> {

    abstract protected RepositoryInterface<T, E, I> repository();

    @Override
    public List<T> findAllByExample(E example) {
        return repository().findAllByExample(example);
    }

    @Override
    public T save(T entity) {
        return repository().save(entity);
    }

    @Override
    public T invalid(I id) {
        T entity = repository().getOne(id);
        // 楽観的排他制御の代わりに現在のステータスをチェック
        if (Status.INVALID.getCodeValue().equals(entity.getStatus())) {
            // TODO ステータスが同じなため、更新できないメッセージの表示
        }
        entity.setStatus(Status.INVALID.getCodeValue());
        return repository().save(entity);
    }

    @Override
    public T valid(I id) {
        T entity = repository().getOne(id);
        // 楽観的排他制御の代わりに現在のステータスをチェック
        if (Status.VALID.getCodeValue().equals(entity.getStatus())) {
            // TODO ステータスが同じなため、更新できないメッセージの表示
        }
        entity.setStatus(Status.VALID.getCodeValue());
        return repository().save(entity);
    }

    @Override
    public void delete(I id) {
        repository().deleteById(id);
    }

    @Override
    public boolean equalsEntity(T entity, T other) {

        // todo 比較メソッドをエンティティ側に実装予定

        return false;
    }

    @Override
    public T findById(I id) {
        return repository().getOne(id);
    }

    /**
     * 保存前処理(Override用)
     *
     * @param entity  更新内容のエンティティ
     * @param current 更新前のエンティティ
     */
    protected void beforeSave(T entity, T current) {
    }

    /**
     * 保存後処理(Override用)
     *
     * @param entity  更新内容のエンティティ
     * @param current 更新前のエンティティ
     */
    protected void afterSave(T entity, T current) {
    }
}
