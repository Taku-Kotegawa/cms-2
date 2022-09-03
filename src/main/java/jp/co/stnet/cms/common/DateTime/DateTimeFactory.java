package jp.co.stnet.cms.common.DateTime;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DateTimeFactory {

    public LocalDateTime getNow(){
        return LocalDateTime.now();
    };

}
