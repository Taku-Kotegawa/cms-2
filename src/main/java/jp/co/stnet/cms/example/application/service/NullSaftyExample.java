package jp.co.stnet.cms.example.application.service;


import org.springframework.lang.NonNull;

public interface NullSaftyExample {

    @NonNull
    String nullableTest(String x, String y);

}
