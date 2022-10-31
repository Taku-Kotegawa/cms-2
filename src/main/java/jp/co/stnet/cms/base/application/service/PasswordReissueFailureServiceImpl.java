package jp.co.stnet.cms.base.application.service;

import jp.co.stnet.cms.base.application.repository.FailedPasswordReissueRepository;
import jp.co.stnet.cms.base.domain.model.mbg.FailedPasswordReissue;
import jp.co.stnet.cms.common.datetime.DateTimeFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class PasswordReissueFailureServiceImpl implements PasswordReissueFailureService {

    private final FailedPasswordReissueRepository failedPasswordReissueRepository;
    private final DateTimeFactory dateTimeFactory;

    @Override
    public void resetFailure(String username, String token) {
        failedPasswordReissueRepository.register(
                FailedPasswordReissue.builder()
                        .token(token)
                        .attemptDate(dateTimeFactory.getNow())
                        .build()
        );
    }
}
