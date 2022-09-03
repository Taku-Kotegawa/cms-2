package jp.co.stnet.cms.base.application.service;

import jp.co.stnet.cms.base.application.repository.FailedAuthenticationRepository;
import jp.co.stnet.cms.base.application.repository.SuccessfulAuthenticationRepository;
import jp.co.stnet.cms.base.domain.model.mbg.FailedAuthentication;
import jp.co.stnet.cms.base.domain.model.mbg.FailedAuthenticationExample;
import jp.co.stnet.cms.base.domain.model.mbg.SuccessfulAuthentication;
import jp.co.stnet.cms.base.domain.model.mbg.SuccessfulAuthenticationExample;
import jp.co.stnet.cms.common.DateTime.DateTimeFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class AuthenticationEventServiceImpl implements AuthenticationEventService {

    private final SuccessfulAuthenticationRepository successfulAuthenticationRepository;
    private final FailedAuthenticationRepository failedAuthenticationRepository;
    private final DateTimeFactory dateTimeFactory;

    @Transactional(readOnly = true)
    @Override
    public SuccessfulAuthentication findLatestSuccessEvents(String username) {
        var example = new SuccessfulAuthenticationExample();
        example.or().andUsernameEqualTo(username);
        example.setOrderByClause("authenticationTimestamp DESC");

        var rowBounds = new RowBounds(0, 1);
        var result = successfulAuthenticationRepository.selectByExampleWithRowbounds(example, rowBounds);

        if (result.isEmpty()) {
            return null;
        } else {
            return result.get(0);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public FailedAuthentication findLatestFailureEvents(String username) {
        var example = new FailedAuthenticationExample();
        example.or().andUsernameEqualTo(username);
        example.setOrderByClause("authenticationTimestamp DESC");

        var rowBounds = new RowBounds(0, 1);
        var result = failedAuthenticationRepository.selectByExampleWithRowbounds(example, rowBounds);

        if (result.isEmpty()) {
            return null;
        } else {
            return result.get(0);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void authenticationSuccess(String username) {
        var row = new SuccessfulAuthentication();
        row.setUsername(username);
        row.setAuthenticationTimestamp(dateTimeFactory.getNow());
        successfulAuthenticationRepository.insert(row);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void authenticationFailure(String username) {
        var row = new FailedAuthentication();
        row.setUsername(username);
        row.setAuthenticationTimestamp(dateTimeFactory.getNow());
        failedAuthenticationRepository.insert(row);
    }

    @Override
    public long deleteFailureEventByUsername(String username) {
        return failedAuthenticationRepository.deleteByeUsername(username);
    }
}