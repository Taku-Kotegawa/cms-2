package jp.co.stnet.cms.example.application.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class NullSaftyExampleImplTest {

    NullSaftyExample target = new NullSaftyExampleImpl();

    private final Random random = new Random();

    @Test
    void nullableTest() {

        String x;
        String y;

        if (random.nextInt(10) > 5) {
            x = null;
            y = null;
        } else {
            x = "a";
            y = "b";
        }

        var result = target.nullableTest(x, y);

        System.out.println(result);

        Objects.requireNonNull(result);

    }
}