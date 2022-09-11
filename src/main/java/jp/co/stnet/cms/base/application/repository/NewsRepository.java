package jp.co.stnet.cms.base.application.repository;


import jp.co.stnet.cms.base.domain.model.mbg.Variable;
import jp.co.stnet.cms.base.infrastructure.mapper.mbg.VariableMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Component
public class NewsRepository {

    private final VariableMapper mapper;

    /**
     * VariableテーブルをIDで検索してデータを取得
     *
     * @param id データのID
     * @return
     */
    public Optional<Variable> findById(Long id){
        return Optional.ofNullable(mapper.selectByPrimaryKey(id));
    };


    /**
     * Variableテーブルから以下の条件に合致するものを検索
     * ・type：NEWS
     * ・STATUS：1
     * ・DATE1 <= 検索日　<= DATE2
     *
     * @param type   　TYPE
     * @param status 　STATUS
     * @param date1  　検索日
     * @param date2  　検索日
     * @return
     */
    List<Variable> findByTypeAndStatusAndDate1LessThanEqualAndDate2GreaterThanEqual(String type, String status, LocalDate date1, LocalDate date2){
        return null;
    };
}
