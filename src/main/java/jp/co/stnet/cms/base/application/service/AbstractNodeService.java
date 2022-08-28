package jp.co.stnet.cms.base.application.service;


import jp.co.stnet.cms.base.application.repository.AbstractVersionRepository;
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

import java.util.Optional;

@Slf4j
public abstract class AbstractNodeService<T extends KeyInterface<I> & VersionInterface & StatusInterface, E, I> implements NodeIService<T, E, I> {

    abstract protected VersionMapperInterface<T, E, I> repository();

    @Override
    public Iterable<T> findAllByExample(E example) {
        return repository().selectByExample(example);
    }

    @Override
    public T save(T entity) {
        if (entity.getVersion() == null) {
            // Versionが取得でいない場合は、insert
            repository().insert(entity);

        } else {
            // Versionが設定されている場合は、update
            long count = repository().updateByPrimaryKeyAndVersion(entity);
            if (count == 0) {
                //更新失敗(先にデータが更新された or 削除された)
                // todo 例外処理の追加
            }
        }

        return repository().selectByPrimaryKey(entity.getId());
    }

    @Override
    public T invalid(I i) {
        T entity = repository().selectByPrimaryKey(i);

        // 楽観的排他制御の代わりに現在のステータスをチェック
        if (Status.INVALID.getCodeValue().equals(entity.getStatus())) {
            // ステータスが同じなため、更新できない。
        }

        entity.setStatus(Status.INVALID.getCodeValue());
        long count = repository().updateByPrimaryKey(entity);
        if (count == 0) {
            // なんらかの理由で更新失敗(直前に物理削除されたなど)
            // todo 例外処理
        }

        return repository().selectByPrimaryKey(i);
    }

    @Override
    public T valid(I i) {
        T entity = repository().selectByPrimaryKey(i);

        // 楽観的排他制御の代わりに現在のステータスをチェック
        if (Status.VALID.getCodeValue().equals(entity.getStatus())) {
            // ステータスが同じなため、更新できない。
        }

        entity.setStatus(Status.VALID.getCodeValue());
        long count = repository().updateByPrimaryKey(entity);
        if (count == 0) {
            // なんらかの理由で更新失敗(直前に物理削除されたなど)
            throw new OptimisticLockingFailureBusinessException(ResultMessages.error().add(MessageKeys.E_CM_FW_8001));
        }

        return repository().selectByPrimaryKey(i);
    }

    @Override
    public void delete(I i) {
        repository().deleteByPrimaryKey(i);
    }

    @Override
    public boolean equalsEntity(T entity, T other) {

        // todo 比較メソッドをエンティティ側に実装予定


        return false;
    }

    @Override
    public T findById(I i) {
        return Optional.ofNullable(repository().selectByPrimaryKey(i))
                .orElseThrow(() -> new ResourceNotFoundException(ResultMessages.error().add(MessageKeys.E_SL_FW_5001, i)));
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
