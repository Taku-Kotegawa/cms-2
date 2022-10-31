package jp.co.stnet.cms.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.sql.DataSource;

@EnableBatchProcessing
@EnableAsync
@Configuration
public class AsyncBatchConfig {

    @Autowired
    DataSource dataSource;

    @Autowired
    JobRepository jobRepository;

    @Autowired
    TaskExecutor taskExecutor;

    // spring.main.allow-bean-definition-overriding=true が必要
    @Bean
    public JobLauncher jobLauncher() throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(taskExecutor);
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }

}
