package jp.co.stnet.cms.base.application.service;

import jp.co.stnet.cms.base.application.repository.NewsRepository;
import jp.co.stnet.cms.base.domain.model.mbg.Variable;
import jp.co.stnet.cms.common.datetime.DateTimeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    DateTimeFactory dateTimeFactory;

    private final String TYPE = "NEWS";
    private final String STATUS = "1";

    /**
     * お知らせに表示するデータの取得
     *
     * @return List Variable
     */
    public List<Variable> findOpenNews() {

        //検索日の取得
        LocalDate localDate = dateTimeFactory.getNow().toLocalDate();

        //TYPE,STATUS,検索日から
        // お知らせ一覧に表示するVariableをListで取得
        List<Variable> variableList = new ArrayList<>();
        return variableList;
    }

    /**
     * IDからお知らせ詳細に表示するデータの取得
     *
     * @return Optional Variable
     */
    public Optional<Variable> newsDetail(Long id) {

        //ID検索からお知らせ詳細に表示する
        Optional<Variable> variableList = newsRepository.findById(id);
        return variableList;
    }


}
