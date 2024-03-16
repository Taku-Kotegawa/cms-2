package jp.co.stnet.cms.base.application.repository;

import jp.co.stnet.cms.base.domain.model.mbg.PasswordHistory;
import jp.co.stnet.cms.base.domain.model.mbg.PasswordHistoryExample;
import jp.co.stnet.cms.base.domain.model.mbg.PasswordHistoryKey;
import jp.co.stnet.cms.base.infrastructure.mapper.MapperInterface;
import jp.co.stnet.cms.base.infrastructure.mapper.mbg.PasswordHistoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.rightPad;

@SpringBootTest
@Transactional
class PasswordHistoryRepositoryTest extends AbstractRepositoryStringIdTest<PasswordHistory, PasswordHistoryExample, PasswordHistoryKey> {

    @Autowired
    PasswordHistoryRepository target;

    @Autowired
    PasswordHistoryMapper mapper;

    @Override
    AbstractRepository<PasswordHistory, PasswordHistoryExample, PasswordHistoryKey> target() {
        return target;
    }

    @Override
    MapperInterface<PasswordHistory, PasswordHistoryExample, PasswordHistoryKey> mapper() {
        return mapper;
    }

    @Override
    PasswordHistory createEntity(String id) {
        return PasswordHistory.builder()
                .username(rightPad("username:" + id, 128, "0"))
                .password(rightPad("password:" + id, 128, "0"))
                .useFrom(LocalDateTime.of(1111, 11, 11, 11, 11, 11, 111111000))
                .createdBy(rightPad("createdBy:" + id, 255, "0"))
                .build();
    }

    @Override
    PasswordHistoryExample newExample() {
        return new PasswordHistoryExample();
    }

    @Override
    void setOverflowValue(PasswordHistory entity) {
        entity.setPassword(entity.getPassword() + "a");
    }

    @Override
    void changeField(PasswordHistory entity) {
        entity.setPassword("change");
    }

    @Override
    void fixVersion(PasswordHistory entity) {
        // 該当なし
    }

    @Override
    void fixVersionList(List<PasswordHistory> entities) {
        // 該当なし
    }

    @Override
    void setFindByCondition(PasswordHistoryExample example) {
        example.or().andUsernameEqualTo(createEntity("01").getUsername());
    }

    @Override
    void setNotFindByCondition(PasswordHistoryExample example) {
        example.or().andUsernameEqualTo(createEntity("not exists").getUsername());
    }

    @Override
    void setOrderByClause(PasswordHistoryExample example) {
        example.setOrderByClause("username desc");
    }
}