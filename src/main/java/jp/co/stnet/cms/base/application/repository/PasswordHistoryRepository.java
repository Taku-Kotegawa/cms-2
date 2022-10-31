package jp.co.stnet.cms.base.application.repository;


import jp.co.stnet.cms.base.domain.model.mbg.PasswordHistory;
import jp.co.stnet.cms.base.domain.model.mbg.PasswordHistoryExample;
import jp.co.stnet.cms.base.domain.model.mbg.PasswordHistoryKey;
import jp.co.stnet.cms.base.infrastructure.mapper.MapperInterface;
import jp.co.stnet.cms.base.infrastructure.mapper.mbg.PasswordHistoryMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Component
public class PasswordHistoryRepository extends AbstractRepository<PasswordHistory, PasswordHistoryExample, PasswordHistoryKey> {

    private final PasswordHistoryMapper mapper;

    @Override
    MapperInterface<PasswordHistory, PasswordHistoryExample, PasswordHistoryKey> mapper() {
        return mapper;
    }

    @Override
    PasswordHistoryExample example() {
        return new PasswordHistoryExample();
    }

    /**
     * @param entity
     * @return
     */
    public int insert(PasswordHistory entity) {
        return mapper.insert(entity);
    }

    /**
     * ユーザ名で検索する。
     *
     * @param username ユーザ名
     * @return パスワード履歴のリスト
     */
    public List<PasswordHistory> findByUsername(String username) {
        var example = new PasswordHistoryExample();
        example.or()
                .andUsernameEqualTo(username);
        return mapper.selectByExample(example);
    }

    /**
     * 指定したユーザ名と一致し、利用開始日より新しいデータを検索する。
     *
     * @param username ユーザ名
     * @param useFrom  利用開始日
     * @return ヒットしたパスワード履歴のリスト
     */
    public List<PasswordHistory> findByUsernameAndUseFromAfter(String username, LocalDateTime useFrom) {
        var example = new PasswordHistoryExample();
        example.or()
                .andUsernameEqualTo(username)
                .andUseFromGreaterThan(useFrom);
        return mapper.selectByExample(example);

    }

    /**
     * @param username
     * @return
     */
    public List<PasswordHistory> findLatest(String username) {
        var example = new PasswordHistoryExample();
        example.or().andUsernameEqualTo(username);
        example.setOrderByClause("use_from DESC");
        var rowBounds = new RowBounds(0, 1);
        return mapper.selectByExampleWithRowbounds(example, rowBounds);
    }

}
