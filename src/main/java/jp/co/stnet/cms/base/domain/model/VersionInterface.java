package jp.co.stnet.cms.base.domain.model;

public interface VersionInterface {

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
}
