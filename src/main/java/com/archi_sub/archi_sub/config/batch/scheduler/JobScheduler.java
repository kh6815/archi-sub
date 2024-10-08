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
    private final Job deleteFileJob;
    private final Job deleteNotificationJob;


    /*
    *   0: 초 (0초)
        1: 분 (1분)
        0: 시 (0시, 즉 자정)
        *: 일 (매일)
        *: 월 (매월)
        *: 요일 (모든 요일)
    * */

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

    @Scheduled(cron="0 0 1 * * 1" , zone="Asia/Seoul")   //매주 월요일 새벽 1시
//    @Scheduled(cron="0 1 0 * * *" , zone="Asia/Seoul")   //매일 00:01:00
//    @Scheduled(cron = "*/5 * * * * *", zone = "Asia/Seoul")
    public void runDeleteFileJob() throws Exception {
        String formatTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS")
                .format(new Date());

        JobParameters params = new JobParametersBuilder()
                .addString("time", formatTime) // 매번 다른 파라미터로 실행
                .toJobParameters();

        try{
            JobExecution jobExecution = jobLauncher.run(deleteFileJob, params);

            while (jobExecution.isRunning()) {
                log.info("RunDeleteFileJob...");
            }

            log.info("DeleteFileJob Execution: " + jobExecution.getStatus());
    //            log.info("BestContentJob getJobConfigurationName: " + jobExecution.getJobConfigurationName());
            log.info("DeleteFileJob getJobId: " + jobExecution.getJobId());
            log.info("DeleteFileJob getExitStatus: " + jobExecution.getExitStatus());
            log.info("DeleteFileJob getJobInstance: " + jobExecution.getJobInstance());
            log.info("DeleteFileJob getStepExecutions: " + jobExecution.getStepExecutions());
            log.info("DeleteFileJob getLastUpdated: " + jobExecution.getLastUpdated());
            log.info("DeleteFileJob getFailureExceptions: " + jobExecution.getFailureExceptions());
        } catch(JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException | JobParametersInvalidException | JobRestartException e) {
            log.error(e.getMessage());
        }
    }

    @Scheduled(cron="0 0 4 * * 1" , zone="Asia/Seoul")   // 매주 월요일 새벽 4시
//    @Scheduled(cron = "*/5 * * * * *", zone = "Asia/Seoul")
    public void runDeleteNotificationJob() throws Exception {
        String formatTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS")
                .format(new Date());

        JobParameters params = new JobParametersBuilder()
                .addString("time", formatTime) // 매번 다른 파라미터로 실행
                .toJobParameters();

        try{
            JobExecution jobExecution = jobLauncher.run(deleteNotificationJob, params);

            while (jobExecution.isRunning()) {
                log.info("RunDeleteNotificationJob...");
            }

            log.info("RunDeleteNotificationJob Execution: " + jobExecution.getStatus());
            log.info("RunDeleteNotificationJob getJobId: " + jobExecution.getJobId());
            log.info("RunDeleteNotificationJob getExitStatus: " + jobExecution.getExitStatus());
            log.info("RunDeleteNotificationJob getJobInstance: " + jobExecution.getJobInstance());
            log.info("RunDeleteNotificationJob getStepExecutions: " + jobExecution.getStepExecutions());
            log.info("RunDeleteNotificationJob getLastUpdated: " + jobExecution.getLastUpdated());
            log.info("RunDeleteNotificationJob getFailureExceptions: " + jobExecution.getFailureExceptions());
        } catch(JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException | JobParametersInvalidException | JobRestartException e) {
            log.error(e.getMessage());
        }
    }
}
