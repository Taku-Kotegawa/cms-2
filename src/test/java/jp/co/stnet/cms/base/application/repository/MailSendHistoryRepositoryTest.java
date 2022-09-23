package jp.co.stnet.cms.base.application.repository;

import jp.co.stnet.cms.base.domain.model.mbg.MailSendHistory;
import jp.co.stnet.cms.base.domain.model.mbg.MailSendHistoryExample;
import jp.co.stnet.cms.base.infrastructure.mapper.MapperInterface;
import jp.co.stnet.cms.base.infrastructure.mapper.mbg.MailSendHistoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.rightPad;

@SpringBootTest
@Transactional
class MailSendHistoryRepositoryTest extends AbstractRepositoryLongIdTest<MailSendHistory, MailSendHistoryExample, Long> {

    @Autowired
    MailSendHistoryRepository target;

    @Autowired
    MailSendHistoryMapper mapper;

    @Override
    AbstractRepository<MailSendHistory, MailSendHistoryExample, Long> target() {
        return target;
    }

    @Override
    MapperInterface<MailSendHistory, MailSendHistoryExample, Long> mapper() {
        return mapper;
    }

    @Override
    MailSendHistory createEntity(String id) {
        return MailSendHistory.builder()
                .subject(rightPad("subject:" + id, 255, "0"))

                .build();
    }

    @Override
    MailSendHistoryExample newExample() {
        return new MailSendHistoryExample();
    }

    @Override
    void setOverflowValue(MailSendHistory entity) {
        entity.setSubject(entity.getSubject() + "a");
    }

    @Override
    void changeField(MailSendHistory entity) {
        entity.setSubject("change");
    }

    @Override
    void fixVersion(MailSendHistory entity) {
        // 該当なし
    }

    @Override
    void fixVersionList(List<MailSendHistory> entities) {
        // 該当なし
    }

    @Override
    Long notExistId() {
        return 999L;
    }

    @Override
    List<Long> notExistsIds() {
        return List.of(998L, 999L);
    }

    @Override
    void setFindByCondition(MailSendHistoryExample example) {
        example.or().andSubjectEqualTo(createEntity("01").getSubject());
    }

    @Override
    void setNotFindByCondition(MailSendHistoryExample example) {
        example.or().andIdEqualTo(999L);
    }

    @Override
    void setOrderByClause(MailSendHistoryExample example) {
        example.setOrderByClause("subject desc");
    }
}