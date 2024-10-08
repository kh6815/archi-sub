package com.archi_sub.archi_sub.config.batch.listener;

import com.archi_sub.archi_sub.db.repository.content.BestContentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class BestContentStepExceptionListener implements StepExecutionListener {

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
        } else if (stepExecution.getStatus() == BatchStatus.FAILED) {
            log.error("Step 실패: {}", stepExecution.getStepName());
            // 실패 시 사용자 정의 로직 추가
            Throwable exception = stepExecution.getFailureExceptions().get(0);
            handleException(exception);
        }
        return new ExitStatus("stepListener exit");
    }

    private void handleException(Throwable exception) {
        // 예외 처리 로직
        log.error("Exception occurred: ", exception);
        // 필요 시 알림 또는 후속 처리 로직 추가
    }
}
