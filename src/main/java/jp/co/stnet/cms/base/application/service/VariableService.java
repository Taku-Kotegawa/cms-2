package jp.co.stnet.cms.base.application.service;

import jp.co.stnet.cms.base.domain.model.mbg.Variable;
import jp.co.stnet.cms.base.domain.model.mbg.VariableExample;

import java.util.List;

/**
 * VariableService
 */
public interface VariableService extends NodeIService<Variable, VariableExample, Long> {

    /**
     * タイプで検索する。
     *
     * @param type タイプ
     * @return ヒットしたデータのリスト、コードの昇順
     */
    List<Variable> findAllByType(String type);

    /**
     * タイプとコードで検索する。
     *
     * @param type タイプ
     * @param code コード
     * @return ヒットしたデータのリスト
     */
    List<Variable> findAllByTypeAndCode(String type, String code);

    /**
     * タイプとvalueXの値で検索する。(X = 1 〜 10)
     *
     * @param type  タイプ
     * @param i     valueXのXを示す整数(1 - 10)
     * @param value valueXの値
     * @return ヒットしたデータのリスト
     */
    List<Variable> findAllByTypeAndValueX(String type, int i, String value);

}
