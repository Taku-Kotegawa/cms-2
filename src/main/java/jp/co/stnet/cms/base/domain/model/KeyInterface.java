package jp.co.stnet.cms.base.domain.model;

public interface KeyInterface<I> {

    /**
     * ID(主キー)の取得
     * modelType=flatの場合、複合主キーに対応していない
     *
     * @return 主キーの値 or 主キークラス(複合主キーの場合)
     */
    I getId();

}
