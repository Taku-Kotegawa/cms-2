package jp.co.stnet.cms.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableBatchProcessing
@EnableAsync
@Configuration
public class AsyncBatchConfig {

}
