package jp.co.stnet.cms.base.application.repository.interfaces;

import jp.co.stnet.cms.base.domain.model.KeyInterface;
import jp.co.stnet.cms.base.domain.model.VersionInterface;

public interface VersionRepositoryInterface<T extends KeyInterface<I> & VersionInterface, E, I> extends RepositoryInterface<T, E, I> {
}
