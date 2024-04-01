package jp.co.stnet.cms.base.domain.model;

public interface KeyInterface<I> {

    /**
     * ID(主キー)の取得
     *
     * @return 主キーの値 or 主キークラス(複合主キーの場合)
     */
    I getPrimaryKey();

}
