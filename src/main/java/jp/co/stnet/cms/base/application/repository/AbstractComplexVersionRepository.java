package jp.co.stnet.cms.base.application.repository;

import jp.co.stnet.cms.base.application.repository.interfaces.ComplexVersionRepositoryInterface;
import jp.co.stnet.cms.base.domain.model.KeyInterface;
import jp.co.stnet.cms.base.domain.model.VersionInterface;
import jp.co.stnet.cms.base.infrastructure.mapper.VersionMapperInterface;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


/**
 * 複合型エンティティ用のリポジトリ
 * <p>
 * メインのテーブルの前後に子テーブルの削除・挿入を行う。
 * delete -> 前処理
 * register -> 後処理
 * save -> 後処理
 * find -> 後処理
 * 複数処理は単数処理の繰り返し
 *
 * @param <S>
 * @param <T>
 * @param <E>
 * @param <I>
 */
@Transactional
public abstract class AbstractComplexVersionRepository<T extends KeyInterface<I> & VersionInterface, E, I, S extends T> implements ComplexVersionRepositoryInterface<T, E, I, S> {

    private static final int DEFAULT_PAGE_SIZE = 5;

    protected abstract VersionMapperInterface<T, E, I> mapper();

    protected abstract E example();

    /**
     * deleteAll()メインテーブル削除時の前処理
     */
    protected abstract void beforeDeleteAll();


    /**
     * メインテーブルから１件削除時の前処理
     *
     * @param id 削除するID
     */
    protected abstract void beforeDelete(I id);

    /**
     * メインテーブル新規登録時の前処理
     *
     * @param entity 引数で指定されたエンティティ
     */
    protected abstract void afterRegister(S entity);

    /**
     * メインテーブル更新時の前処理
     *
     * @param entity 引数で指定されたエンティティ
     */
    protected abstract void afterSave(S entity);

    /**
     * メインテーブル検索時の後処理
     *
     * @param entity 加工前のエンティティ
     */
    protected abstract void afterFind(S entity);

    /**
     * スーパクラスからサブクラスのエンティティを作成
     *
     * @param entity スーパクラス
     * @return サブクラス
     */
    protected abstract S cast(T entity);

    @Override
    @Transactional(readOnly = true)
    public List<S> findAllById(List<I> ids) {
        Objects.requireNonNull(ids);
        var result = new ArrayList<S>();
        for (I id : ids) {
            var entity = findById(id);
            entity.ifPresent(result::add);
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<S> findAllByExample(E example) {
        var entities = mapper().selectByExample(example).stream().map(this::cast).toList();
        entities.forEach(this::afterFind);
        return entities;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<S> findAllByExampleWithRowBounds(E example, RowBounds rowBounds) {
        var totalCount = mapper().countByExample(null);
        var entities = mapper().selectByExampleWithRowbounds(example, rowBounds).stream().map(this::cast).toList();
        entities.forEach(this::afterFind);
        Pageable pageable = PageRequest.of(rowBounds.getOffset() / rowBounds.getLimit(), rowBounds.getLimit());
        return new PageImpl<>(entities, pageable, totalCount);
    }

    @Override
    @Transactional(readOnly = true)
    public List<S> findAll() {
        var entities = mapper().selectByExample(example()).stream().map(this::cast).toList();
        entities.forEach(this::afterFind);
        return entities;
    }

    @Override
    public S register(S entity) {
        Objects.requireNonNull(entity);
        mapper().insert(entity);
        afterRegister(entity);
        return getOne(entity.getId());
    }

    @Override
    public List<S> registerAll(List<S> entities) {
        Objects.requireNonNull(entities);
        var result = new ArrayList<S>();
        for (var entity : entities) {
            result.add(register(entity));
        }
        return result;
    }

    @Override
    public S save(S entity) {
        Objects.requireNonNull(entity);
        if (!existsById(entity.getId())) {
            return register(entity);
        }
        int count = mapper().updateByPrimaryKeyAndVersion(entity);
        if (count != 1) {
            // 楽観的排他制御でエラー
            T before = mapper().selectByPrimaryKey(entity.getId());
            throw new OptimisticLockingFailureException("after = " + entity + ", before = " + before);
        }
        afterSave(entity);
        return getOne(entity.getId());
    }

    @Override
    public List<S> saveAll(List<S> entities) {
        Objects.requireNonNull(entities);
        var result = new ArrayList<S>();
        for (var entity : entities) {
            result.add(save(entity));
        }
        return result;
    }

    @Override
    public Optional<S> findById(I id) {
        Objects.requireNonNull(id);
        var entity = cast(mapper().selectByPrimaryKey(id));
        afterFind(entity);
        return Optional.ofNullable(entity);
    }

    @Override
    public S getOne(I id) {
        Objects.requireNonNull(id);
        return findById(id).orElseThrow(() -> new ResourceNotFoundException("id = " + id));
    }

    @Override
    public boolean existsById(I id) {
        return mapper().selectByPrimaryKey(id) != null;
    }

    @Override
    public void delete(S entity) {
        Objects.requireNonNull(entity);
        deleteById(entity.getId());
    }

    @Override
    public void deleteById(I id) {
        Objects.requireNonNull(id);
        beforeDelete(id);
        mapper().deleteByPrimaryKey(id);
    }

    @Override
    public void deleteAll() {
        beforeDeleteAll();
        mapper().deleteByExample(example());
    }

    @Override
    public void deleteAll(List<S> entities) {
        Objects.requireNonNull(entities);
        deleteAllById(entities.stream().map(KeyInterface::getId).toList());
    }

    @Override
    public void deleteAllById(List<I> ids) {
        Objects.requireNonNull(ids);
        ids.forEach(this::deleteById);
    }

    @Override
    public void deleteByExample(E example) {
        var entities = mapper().selectByExample(example).stream().map(this::cast).toList();
        deleteAll(entities);
    }

}
