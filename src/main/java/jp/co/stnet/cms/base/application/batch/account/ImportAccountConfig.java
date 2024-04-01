package jp.co.stnet.cms.base.application.batch.account;

import jp.co.stnet.cms.common.constant.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@RequiredArgsConstructor
@Configuration
public class ImportAccountConfig {

    private static final String JOB_ID = Constants.JOBID.IMPORT_ACCOUNT;

    private static final String TASKLET_NAME = JOB_ID + "Tasklet";


    private final JobRepository jobRepository;

    private final PlatformTransactionManager txManager;

    @Qualifier(TASKLET_NAME)
    private final Tasklet tasklet;

    /**
     * メソッド名でBean定義される点に注意
     *
     * @return
     */
    @Bean(name = JOB_ID)
    public Job importAccount() {

        Step step1 = new StepBuilder("step1", jobRepository)
                .tasklet(tasklet, txManager)
                .build();

        return new JobBuilder(JOB_ID, jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step1)
                .build();

    }

}
