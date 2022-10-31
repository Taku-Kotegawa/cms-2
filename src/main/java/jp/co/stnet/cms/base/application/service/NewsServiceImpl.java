package jp.co.stnet.cms.base.application.service;

import jp.co.stnet.cms.base.application.repository.NewsRepository;
import jp.co.stnet.cms.base.domain.enums.Status;
import jp.co.stnet.cms.base.domain.enums.VariableType;
import jp.co.stnet.cms.base.domain.model.mbg.Variable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;

    private static final VariableType TYPE = VariableType.NEWS;
    private static final Status STATUS = Status.VALID;

    /**
     * お知らせに表示するデータの取得
     *
     * @return List Variable
     */
    @Override
    @Transactional(readOnly = true)
    public List<Variable> findOpenNews() {

        // TODO TYPE,STATUS,検索日から
        List<Variable> variableList = new ArrayList<>();

        return variableList;
    }

    /**
     * IDからお知らせ詳細に表示するデータの取得
     *
     * @return Optional Variable
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Variable> newsDetail(Long id) {

        //ID検索からお知らせ詳細に表示する
        Optional<Variable> variableList = newsRepository.findById(id);
        return variableList;
    }

}
