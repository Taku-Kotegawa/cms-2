package jp.co.stnet.cms.base.application.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TestService {

    public void throwException() {

        throw new IllegalArgumentException("TestService.throwException. Exception happened.");
    }

}
