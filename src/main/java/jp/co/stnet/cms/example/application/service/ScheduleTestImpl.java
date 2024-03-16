package jp.co.stnet.cms.example.application.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@ConditionalOnProperty(prefix = "ScheduleTest", name="enabled", havingValue="true", matchIfMissing = true)
public class ScheduleTestImpl implements ScheduleTest {

    @Override
//    @Scheduled(initialDelayString = "${ScheduleTest.initialDelay}", fixedDelayString = "${ScheduleTest.fixedDelay}")
    public void test01() {

        log.info("Start ScheduleTest.test01");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        log.info("End ScheduleTest.test01");

    }
}
