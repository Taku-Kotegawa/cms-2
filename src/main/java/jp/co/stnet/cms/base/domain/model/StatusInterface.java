package jp.co.stnet.cms.base.domain.model;

public interface StatusInterface<T> {

    /**
     * ステータスの設定
     *
     * @param status ステータス
     */
    void setStatus(String status);

    /**
     * ステータスの取得
     *
     * @return ステータスのコード
     */
    String getStatus();

    /**
     * バージョンの設定
     *
     * @param version
     */
    void setVersion(Long version);

    /**
     * バージョンの取得
     *
     * @return バージョン番号 (nullの場合、新規登録)
     */
    Long getVersion();

    /**
     * ID(主キー)の取得
     *
     * @return 主キーの値 or 主キークラス(複合主キーの場合)
     */
    T getId();

}
