package jp.co.stnet.cms.base.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class PasswordChangeServiceImpl implements PasswordChangeService {

    private final AccountSharedService accountSharedService;

    @Override
    public boolean updatePassword(String username, String rawPassword) {
        return accountSharedService.updatePassword(username, rawPassword);
    }
}
