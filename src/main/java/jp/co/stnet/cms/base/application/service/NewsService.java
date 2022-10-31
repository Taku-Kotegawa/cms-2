package jp.co.stnet.cms.base.application.service;


import jp.co.stnet.cms.base.domain.model.mbg.Variable;

import java.util.List;
import java.util.Optional;

/**
 * NewsService
 */
public interface NewsService {

    /**
     * お知らせに表示するデータの取得
     *
     * @return List Variable
     */
    List<Variable> findOpenNews();


    /**
     * IDからお知らせ詳細に表示するデータの取得
     *
     * @return Optional Variable
     */
    Optional<Variable> newsDetail(Long id);


}
