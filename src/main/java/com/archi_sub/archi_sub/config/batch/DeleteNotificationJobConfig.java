package com.archi_sub.archi_sub.config.batch;

import com.archi_sub.archi_sub.common.enumobj.BooleanFlag;
import com.archi_sub.archi_sub.config.aws.AwsS3Service;
import com.archi_sub.archi_sub.config.batch.listener.DeleteFileJobExecutionListener;
import com.archi_sub.archi_sub.config.batch.listener.DeleteFileStepExceptionListener;
import com.archi_sub.archi_sub.config.batch.listener.DeleteNotificationJobExecutionListener;
import com.archi_sub.archi_sub.config.batch.listener.DeleteNotificationStepExceptionListener;
import com.archi_sub.archi_sub.db.entity.content.ContentFileEntity;
import com.archi_sub.archi_sub.db.entity.file.FileEntity;
import com.archi_sub.archi_sub.db.entity.notice.NoticeFileEntity;
import com.archi_sub.archi_sub.db.entity.notification.NotificationEntity;
import com.archi_sub.archi_sub.db.entity.user.UserFileEntity;
import com.archi_sub.archi_sub.db.repository.content.ContentFileRepository;
import com.archi_sub.archi_sub.db.repository.file.FileDao;
import com.archi_sub.archi_sub.db.repository.file.FileRepository;
import com.archi_sub.archi_sub.db.repository.notice.NoticeFileRepository;
import com.archi_sub.archi_sub.db.repository.notification.NotificationRepository;
import com.archi_sub.archi_sub.db.repository.user.UserFileRepository;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class DeleteNotificationJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    private final NotificationRepository notificationRepository;

    // 다중 DB일 경우 해당하는 타켓 entityManager를 가져와야한다.
    private final EntityManagerFactory entityManagerFactory;

    private final DeleteNotificationJobExecutionListener deleteNotificationJobExecutionListener;
    private final DeleteNotificationStepExceptionListener deleteNotificationStepExceptionListener;

    @Value("${chunkSize:500}")
    private int chunkSize;

    @Bean
    public Job deleteNotificationJob() {
        return new JobBuilder("deleteNotificationJob", jobRepository)
                .listener(deleteNotificationJobExecutionListener)
                .start(deleteNotificationStep())
                .build();
    }

    // *******************************************
    // user file 삭제

    @Bean
    public Step deleteNotificationStep() {
        return new StepBuilder("deleteNotificationStep", jobRepository)
                .listener(deleteNotificationStepExceptionListener)
                .<NotificationEntity, NotificationEntity> chunk(chunkSize, transactionManager)
                .reader(deleteNotificationReader())
                .writer(deleteNotificationWriter())
                .build();
    }

    @Bean
    public JpaPagingItemReader<NotificationEntity> deleteNotificationReader() {
        return new JpaPagingItemReaderBuilder<NotificationEntity>()
                .name("JpaDeleteNotificationReader") // Item Reader 이름을 설정하여 로그나 예외상황에 필요
                .entityManagerFactory(entityManagerFactory)
                .pageSize(chunkSize)
                .queryString("SELECT n FROM NotificationEntity as n " +
                        "WHERE n.readYn = :readYn  ")
                .parameterValues(Collections.singletonMap("readYn", BooleanFlag.Y))
                .build();
    }

    @Bean
    public ItemWriter<NotificationEntity> deleteNotificationWriter() {
        return items -> {
            if(!items.isEmpty()){
                List<NotificationEntity> deleteNotificationList = new ArrayList<>(items.getItems());
                notificationRepository.deleteAllInBatch(deleteNotificationList);
            }
        };
    }
}
