package jp.co.stnet.cms.base.application.repository;

import jp.co.stnet.cms.base.application.repository.interfaces.RepositoryInterface;
import jp.co.stnet.cms.base.domain.model.KeyInterface;
import jp.co.stnet.cms.base.domain.model.VersionInterface;
import jp.co.stnet.cms.base.infrastructure.mapper.VersionMapperInterface;
import jp.co.stnet.cms.common.util.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Transactional
@Component
public abstract class AbstractVersionRepository<T extends KeyInterface<ID> & VersionInterface, E, ID> extends AbstractRepository<T, E, ID> implements RepositoryInterface<T, E, ID> {

    abstract VersionMapperInterface<T, E, ID> mapper();

    @Override
    public T save(T entity) {
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
        return mapper().selectByPrimaryKey(entity.getId());
    }

}
