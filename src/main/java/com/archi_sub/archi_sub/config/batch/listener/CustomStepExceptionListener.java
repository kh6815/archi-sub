package com.archi_sub.archi_sub.config.batch.listener;

import com.archi_sub.archi_sub.db.repository.content.BestContentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomStepExceptionListener implements StepExecutionListener {

    private final BestContentRepository bestContentRepository;

    @Override
    public void beforeStep(StepExecution stepExecution) {
//        if(stepExecution.getStatus() == BatchStatus.STARTED) {
//            log.info("chunkStep start!");
//        }
        log.info("chunkStep starting: " + stepExecution.getStepName());
        bestContentRepository.deleteAllInBatch();
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        if(stepExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("chunkStep end");
        }
        return new ExitStatus("stepListener exit");
    }
}
