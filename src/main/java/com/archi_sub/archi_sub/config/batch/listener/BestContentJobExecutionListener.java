package com.archi_sub.archi_sub.config.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BestContentJobExecutionListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
//        if(jobExecution.getStatus() == BatchStatus.STARTED) {
//            log.info("chunkJob start! ");
//        }
        log.info("Job starting: " + jobExecution.getJobInstance().getJobName());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
//        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
//            log.info("chunkJob successed! ");
//        } else if (jobExecution.getStatus() == BatchStatus.FAILED) {
//            log.info("chunkJob failed! ");
//        }

        if (jobExecution.getStatus() == BatchStatus.FAILED) {
            log.error("Job failed with exceptions: " + jobExecution.getAllFailureExceptions());
        } else {
            log.info("Job completed with status: " + jobExecution.getStatus());
        }
    }
}
