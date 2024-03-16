package jp.co.stnet.cms.base.infrastructure.mapper;

import jp.co.stnet.cms.base.domain.model.KeyInterface;
import jp.co.stnet.cms.base.domain.model.VersionInterface;

/**
 * MyBatisGeneratorで生成されるMapper Interface共通のメソッドを定義する
 *
 * @param <T> Modelクラス　ex) Account
 */
public interface VersionMapperInterface<T extends KeyInterface<I> & VersionInterface, E, I> extends MapperInterface<T, E, I> {

    /**
     * 1件の更新(楽観的排他用)
     *
     * @param row エンティティ
     * @return 更新件数
     */
    int updateByPrimaryKeyAndVersionSelective(T row);

    /**
     * 1件の更新(楽観的排他用)
     *
     * @param row エンティティ
     * @return 更新件数
     */
    int updateByPrimaryKeyAndVersion(T row);
}
