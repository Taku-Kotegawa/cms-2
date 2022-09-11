package jp.co.stnet.cms.base.application.repository.interfaces;

import jp.co.stnet.cms.base.domain.model.KeyInterface;
import jp.co.stnet.cms.base.domain.model.VersionInterface;

public interface ComplexVersionRepositoryInterface<S extends T, T extends KeyInterface<I> & VersionInterface, E, I>
        extends ComplexRepositoryInterface<S, T, E, I> {
}
