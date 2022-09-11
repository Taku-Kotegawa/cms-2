package jp.co.stnet.cms.base.application.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class PasswordChangeServiceImpl implements PasswordChangeService {

    @Autowired
    AccountService accountService;

    @Override
    public boolean updatePassword(String username, String rawPassword) {
        return accountService.updatePassword(username, rawPassword);
    }
}
