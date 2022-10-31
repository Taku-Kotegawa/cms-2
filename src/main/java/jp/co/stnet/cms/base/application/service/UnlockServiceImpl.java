package jp.co.stnet.cms.base.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class UnlockServiceImpl implements UnlockService {

    private final AuthenticationEventService authenticationEventService;

    @Override
    public void unlock(String username) {
        authenticationEventService.deleteFailureEventByUsername(username);
    }

}
