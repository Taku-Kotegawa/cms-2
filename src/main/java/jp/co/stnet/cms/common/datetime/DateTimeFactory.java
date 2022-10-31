package jp.co.stnet.cms.common.datetime;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DateTimeFactory {

    public LocalDateTime getNow() {
        return LocalDateTime.now();
    }

}
