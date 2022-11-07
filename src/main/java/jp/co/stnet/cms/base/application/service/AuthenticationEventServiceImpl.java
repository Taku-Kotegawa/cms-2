package jp.co.stnet.cms.base.application.service;

import jp.co.stnet.cms.base.application.repository.FailedAuthenticationRepository;
import jp.co.stnet.cms.base.application.repository.SuccessfulAuthenticationRepository;
import jp.co.stnet.cms.base.domain.model.mbg.FailedAuthentication;
import jp.co.stnet.cms.base.domain.model.mbg.FailedAuthenticationExample;
import jp.co.stnet.cms.base.domain.model.mbg.SuccessfulAuthentication;
import jp.co.stnet.cms.base.domain.model.mbg.SuccessfulAuthenticationExample;
import jp.co.stnet.cms.common.datetime.DateTimeFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@Service
public class AuthenticationEventServiceImpl implements AuthenticationEventService {

    private final SuccessfulAuthenticationRepository successfulAuthenticationRepository;
    private final FailedAuthenticationRepository failedAuthenticationRepository;
    private final DateTimeFactory dateTimeFactory;

    @Transactional(readOnly = true)
    @Override
    public List<SuccessfulAuthentication> findLatestSuccessEvents(String username, int count) {
        var example = new SuccessfulAuthenticationExample();
        example.or().andUsernameEqualTo(username);
        example.setOrderByClause("authentication_timestamp DESC");

        var rowBounds = new RowBounds(0, count);

        var result = successfulAuthenticationRepository
                .findAllByExampleWithRowBounds(example, rowBounds);
        return result.getContent();
    }

    @Transactional(readOnly = true)
    @Override
    public List<FailedAuthentication> findLatestFailureEvents(String username, int count) {
        var example = new FailedAuthenticationExample();
        example.or().andUsernameEqualTo(username);
        example.setOrderByClause("authentication_timestamp DESC");

        var rowBounds = new RowBounds(0, count);
        var result = failedAuthenticationRepository.findAllByExampleWithRowBounds(example, rowBounds);
        return result.getContent();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void authenticationSuccess(String username) {
        var row = new SuccessfulAuthentication();
        row.setUsername(username);
        row.setAuthenticationTimestamp(dateTimeFactory.getNow());
        successfulAuthenticationRepository.save(row);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void authenticationFailure(String username) {
        var row = new FailedAuthentication();
        row.setUsername(username);
        row.setAuthenticationTimestamp(dateTimeFactory.getNow());
        failedAuthenticationRepository.save(row);
    }

    @Override
    public long deleteFailureEventByUsername(String username) {
        return failedAuthenticationRepository.deleteByeUsername(username);
    }
}
