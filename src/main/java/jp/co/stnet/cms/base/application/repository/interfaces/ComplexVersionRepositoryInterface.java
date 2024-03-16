package jp.co.stnet.cms.base.application.repository.interfaces;

import jp.co.stnet.cms.base.domain.model.KeyInterface;
import jp.co.stnet.cms.base.domain.model.VersionInterface;

public interface ComplexVersionRepositoryInterface<T extends KeyInterface<I> & VersionInterface, E, I, S extends T>
        extends ComplexRepositoryInterface<T, E, I, S> {
}
