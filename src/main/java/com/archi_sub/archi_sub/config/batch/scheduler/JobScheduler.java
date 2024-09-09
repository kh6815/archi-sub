package com.archi_sub.archi_sub.config.batch.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class JobScheduler {

    private final JobLauncher jobLauncher;
    private final Job bestContentJob;

    @Scheduled(cron = "0 30 2 * * 1") // 매주 월요일 새벽 2시 30분
//    @Scheduled(cron="0 */1 * * * *" , zone="Asia/Seoul")   //매1분마다
    public void runBestContentJob() throws Exception {
        String formatTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS")
                .format(new Date());

        JobParameters params = new JobParametersBuilder()
                .addString("time", formatTime) // 매번 다른 파라미터로 실행
                .toJobParameters();

//        JobParameters params = new JobParametersBuilder()
//                .addLong("time", System.currentTimeMillis()) // 매번 다른 파라미터로 실행
//                .toJobParameters();

        try{
            JobExecution jobExecution = jobLauncher.run(bestContentJob, params);

            while (jobExecution.isRunning()) {
                log.info("RunBestContentJob...");
            }

            log.info("BestContentJob Execution: " + jobExecution.getStatus());
//            log.info("BestContentJob getJobConfigurationName: " + jobExecution.getJobConfigurationName());
            log.info("BestContentJob getJobId: " + jobExecution.getJobId());
            log.info("BestContentJob getExitStatus: " + jobExecution.getExitStatus());
            log.info("BestContentJob getJobInstance: " + jobExecution.getJobInstance());
            log.info("BestContentJob getStepExecutions: " + jobExecution.getStepExecutions());
            log.info("BestContentJob getLastUpdated: " + jobExecution.getLastUpdated());
            log.info("BestContentJob getFailureExceptions: " + jobExecution.getFailureExceptions());
        } catch(JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException | JobParametersInvalidException | JobRestartException e) {
            log.error(e.getMessage());
        }
    }

//    @Scheduled(cron="0 */10 * * * *" , zone="Asia/Seoul")   //매10분마다

//    @Scheduled(cron="0 1 0 * * *" , zone="Asia/Seoul")   //매일 00:01:00
}
