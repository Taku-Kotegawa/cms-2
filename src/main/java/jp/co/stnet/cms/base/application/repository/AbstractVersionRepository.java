package jp.co.stnet.cms.base.application.repository;


import jp.co.stnet.cms.base.domain.model.KeyInterface;
import jp.co.stnet.cms.base.domain.model.VersionInterface;
import jp.co.stnet.cms.base.infrastructure.mapper.VersionMapperInterface;

public abstract class AbstractVersionRepository<T extends KeyInterface<I> & VersionInterface, E, I> extends AbstractRepository<T, E, I> implements VersionMapperInterface<T, E, I> {

    @Override
    VersionMapperInterface<T, E, I> mapper() {
        return null;
    }

    @Override
    public int updateByPrimaryKeyAndVersionSelective(T row) {
        return mapper().updateByPrimaryKeyAndVersionSelective(row);
    }

    @Override
    public int updateByPrimaryKeyAndVersion(T row) {
        return mapper().updateByPrimaryKeyAndVersion(row);
    }
}
