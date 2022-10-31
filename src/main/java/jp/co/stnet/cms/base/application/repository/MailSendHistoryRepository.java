package jp.co.stnet.cms.base.application.repository;


import jp.co.stnet.cms.base.domain.model.mbg.MailSendHistory;
import jp.co.stnet.cms.base.domain.model.mbg.MailSendHistoryExample;
import jp.co.stnet.cms.base.infrastructure.mapper.MapperInterface;
import jp.co.stnet.cms.base.infrastructure.mapper.mbg.MailSendHistoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Component
public class MailSendHistoryRepository extends AbstractRepository<MailSendHistory, MailSendHistoryExample, Long> {

    private final MailSendHistoryMapper mapper;

    @Override
    MapperInterface<MailSendHistory, MailSendHistoryExample, Long> mapper() {
        return mapper;
    }

    @Override
    MailSendHistoryExample example() {
        return new MailSendHistoryExample();
    }
}
