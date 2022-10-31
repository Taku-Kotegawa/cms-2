package jp.co.stnet.cms.base.presentation.controller.job;

import jp.co.stnet.cms.base.domain.enums.Role;
import jp.co.stnet.cms.base.domain.model.LoggedInUser;
import jp.co.stnet.cms.base.presentation.controller.uploadfile.UploadFileForm;
import jp.co.stnet.cms.common.exception.IllegalStateBusinessException;
import jp.co.stnet.cms.common.message.MessageKeys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.terasoluna.gfw.common.message.ResultMessages;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("job")
public class JobController {

    private static final String BASE_PATH = "job";
    private static final String JSP_RESULT = BASE_PATH + "/result";
    private static final String JSP_JOBLOG = BASE_PATH + "/joblog";
    private static final String JSP_SUMMARY = BASE_PATH + "/summary";

    private final JobExplorer jobExplorer;
    private final JdbcTemplate jdbcTemplate;

    @Value("${job.log.dir}")
    String jobLogDir;

    private static final String FIND_EXECUTIONS =
            "SELECT p.JOB_EXECUTION_ID FROM BATCH_JOB_EXECUTION_PARAMS p\n" +
                    "INNER JOIN BATCH_JOB_EXECUTION bje on bje.JOB_EXECUTION_ID = p.JOB_EXECUTION_ID \n" +
                    "INNER JOIN BATCH_JOB_INSTANCE bji on bji.JOB_INSTANCE_ID  = bje.JOB_INSTANCE_ID and bji.JOB_NAME = ?\n" +
                    "WHERE p.KEY_NAME = 'executedBy' AND p.STRING_VAL = ?\n" +
                    "ORDER BY p.JOB_EXECUTION_ID DESC\n" +
                    "LIMIT 20";

    @ModelAttribute
    UploadFileForm setup() {
        return new UploadFileForm();
    }

    /**
     * ユーザが管理者を持っている。
     *
     * @param loggedInUser loggedInUser
     * @return true: 管理者権限あり, false:権限なし
     */
    private boolean isAdmin(LoggedInUser loggedInUser) {
        return loggedInUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + Role.ADMIN.name()));
    }

    @GetMapping("summary")
    public String summary(Model model, @AuthenticationPrincipal LoggedInUser loggedInUser) {
        List<Map<String, Object>> jobSummary = new ArrayList<>();
        var jobNames = jobExplorer.getJobNames();

        if (isAdmin(loggedInUser)) {

            // 管理者はすべてのジョブの結果を確認できる。
            for (String jobName : jobNames) {
                var map = new LinkedHashMap<String, Object>();
                var jobInstance = jobExplorer.getLastJobInstance(jobName);
                map.put("jobName", jobName);
                map.put("jobInstance", jobInstance);
                map.put("jobExecution", jobExplorer.getLastJobExecution(jobInstance));
                jobSummary.add(map);
            }

        } else {
            // 通常は自分が実行したジョブの結果を確認できる。
            for (String jobName : jobNames) {
                var jobExecutionIds = jdbcTemplate.queryForList(FIND_EXECUTIONS, Long.class, loggedInUser.getUsername(), jobName);
                if (!jobExecutionIds.isEmpty()) {
                    var jobExecution = jobExplorer.getJobExecution(jobExecutionIds.get(0));
                    var map = new LinkedHashMap<String, Object>();
                    map.put("jobName", jobName);
                    map.put("jobInstance", jobExecution.getJobInstance());
                    map.put("jobExecution", jobExecution);
                    jobSummary.add(map);
                }
            }

        }

        model.addAttribute("jobSummary", jobSummary);
        return JSP_SUMMARY;
    }


    @GetMapping("result")
    public String result(Model model,
                         @RequestParam(value = "targetjob", required = false) String targetjob,
                         @AuthenticationPrincipal LoggedInUser loggedInUser) {

        List<String> jobList = jobExplorer.getJobNames();

        if (targetjob == null && !jobList.isEmpty()) {
            targetjob = jobList.get(0);
        }

        List<JobInstance> instances = new ArrayList<>();
        List<JobExecution> executions = new ArrayList<>();

        if (isAdmin(loggedInUser)) {
            // 管理者は全ジョブを参照可能
            instances = jobExplorer.getJobInstances(targetjob, 0, 20);
            for (var i : instances) {
                List<JobExecution> list = jobExplorer.getJobExecutions(i);
                executions.addAll(list);
            }

        } else {
            // 通常は自分が実行したジョブを参照可能
            var jobExecutionIds = jdbcTemplate.queryForList(FIND_EXECUTIONS, Long.class, loggedInUser.getUsername(), targetjob);
            for (var jobExecutionId : jobExecutionIds) {
                var jobExecution = jobExplorer.getJobExecution(jobExecutionId);
                instances.add(jobExecution.getJobInstance());
                executions.add(jobExecution);
            }
        }

        model.addAttribute("jobList", jobList);
        model.addAttribute("selectedJob", targetjob);
        model.addAttribute("jobResults", executions);
        return JSP_RESULT;
    }

    @GetMapping("joblog")
    public String jobLog(Model model, @RequestParam(value = "jobexecutionid") Long jobExecutionId,
                         @AuthenticationPrincipal LoggedInUser loggedInUser) {

        if (!isAdmin(loggedInUser)) {
            var jobNames = jobExplorer.getJobNames();
            var jobExecutionIds = new ArrayList<Long>();

            for (String jobName : jobNames) {
                jobExecutionIds.addAll(jdbcTemplate.queryForList(FIND_EXECUTIONS, Long.class, loggedInUser.getUsername(), jobName));
            }

            if (!jobExecutionIds.contains(jobExecutionId)) {
                // 権限なし
                throw new IllegalStateBusinessException(ResultMessages.error().add(MessageKeys.E_SL_FW_5001));
            }
        }

        var jobExecution = jobExplorer.getJobExecution(jobExecutionId);
        if (jobExecution == null) {
            throw new IllegalStateBusinessException(ResultMessages.error().add(MessageKeys.E_SL_FW_5001));
        }

        var jobName = jobExecution.getJobInstance().getJobName();
        var filePath = jobLogDir + "/Job_" + jobName + "_" + jobExecutionId + ".log";
        List<String> fileLines = new ArrayList<String>();
        try {
            fileLines = Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            fileLines.add("ログファイルが見つかりません。");
        }

        model.addAttribute("jobName", jobName);
        model.addAttribute("jobExecutionId", jobExecutionId.toString());
        model.addAttribute("jobLog", fileLines);
        return JSP_JOBLOG;
    }

}
