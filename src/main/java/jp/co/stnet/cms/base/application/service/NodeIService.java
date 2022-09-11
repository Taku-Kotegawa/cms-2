package jp.co.stnet.cms.base.application.service;

import jp.co.stnet.cms.common.datatables.DataTablesInput;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * エンティティ(リビジョン管理なし)用の抽象クラスのインタフェース
 *
 * @param <T> エンティティのクラス(AbstractEntityのサブクラス)
 * @param <E> Exampleクラス
 * @param <I> 主キーのクラス
 */
public interface NodeIService<T, E, I> {

    /**
     * IDで検索
     */
    T findById(I id);

    /**
     * 条件に一致するエンティティのリストを取得
     */
    List<T> findAllByExample(E example);

    /**
     * １件の保存
     */
    T save(T entity);

    /**
     * １件の無効化
     */
    T invalid(I id);

    /**
     * 1件の有効化
     */
    T valid(I id);

    /**
     * １件の削除
     */
    void delete(I id);

    /**
     * エンティティの比較
     *
     * @param entity 比較対象
     * @param other  比較対象
     * @return true:差異なし, false:差異あり
     */
    boolean equalsEntity(T entity, T other);

    /**
     * キーで検索(複数)
     *
     * @param ids キーのリスト
     * @return アカウントのリスト
     */
    List<T> findAllById(List<I> ids);


    <S extends DataTablesInput> Page<T> findPageByInput(S input);
}
