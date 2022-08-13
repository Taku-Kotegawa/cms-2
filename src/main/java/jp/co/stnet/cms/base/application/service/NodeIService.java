package jp.co.stnet.cms.base.application.service;

/**
 * エンティティ(リビジョン管理なし)用の抽象クラスのインタフェース
 *
 * @param <T>  エンティティのクラス(AbstractEntityのサブクラス)
 * @param <ID> 主キーのクラス
 */
public interface NodeIService<T, U, ID> {

    /**
     * IDで検索
     */
    T findById(ID id);

    /**
     * 条件に一致するエンティティのリストを取得
     */
    Iterable<T> findAllByExample(U example);

    /**
     * １件の保存
     */
    T save(T entity);

    /**
     * １件の無効化
     */
    T invalid(ID id);

    /**
     * 1件の有効化
     */
    T valid(ID id);

    /**
     * １件の削除
     */
    void delete(ID id);

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
    Iterable<T> findAllById(Iterable<ID> ids);
}
