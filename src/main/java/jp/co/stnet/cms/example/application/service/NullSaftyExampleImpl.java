package jp.co.stnet.cms.example.application.service;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class NullSaftyExampleImpl implements NullSaftyExample {

    @NonNull
    @Override
    public String nullableTest(String x, String y) {
        return null;
    }

}
