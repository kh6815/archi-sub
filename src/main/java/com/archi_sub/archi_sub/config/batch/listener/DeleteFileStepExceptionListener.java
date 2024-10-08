package com.archi_sub.archi_sub.config.batch.listener;

import com.archi_sub.archi_sub.config.aws.AwsS3Service;
import com.archi_sub.archi_sub.db.entity.file.FileEntity;
import com.archi_sub.archi_sub.db.entity.user.UserFileEntity;
import com.archi_sub.archi_sub.db.repository.content.BestContentRepository;
import com.archi_sub.archi_sub.db.repository.file.FileDao;
import com.archi_sub.archi_sub.db.repository.file.FileRepository;
import com.archi_sub.archi_sub.db.repository.user.UserFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class DeleteFileStepExceptionListener implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("chunkStep starting: " + stepExecution.getStepName());
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
