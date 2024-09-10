package com.archi_sub.archi_sub.config.batch;

import com.archi_sub.archi_sub.common.enumobj.BooleanFlag;
import com.archi_sub.archi_sub.config.aws.AwsS3Service;
import com.archi_sub.archi_sub.config.batch.listener.BestContentJobExecutionListener;
import com.archi_sub.archi_sub.config.batch.listener.BestContentStepExceptionListener;
import com.archi_sub.archi_sub.config.batch.listener.DeleteFileJobExecutionListener;
import com.archi_sub.archi_sub.config.batch.listener.DeleteFileStepExceptionListener;
import com.archi_sub.archi_sub.db.entity.content.BestContentEntity;
import com.archi_sub.archi_sub.db.entity.content.ContentEntity;
import com.archi_sub.archi_sub.db.entity.content.ContentFileEntity;
import com.archi_sub.archi_sub.db.entity.file.FileEntity;
import com.archi_sub.archi_sub.db.entity.user.UserFileEntity;
import com.archi_sub.archi_sub.db.repository.content.ContentFileRepository;
import com.archi_sub.archi_sub.db.repository.file.FileDao;
import com.archi_sub.archi_sub.db.repository.file.FileRepository;
import com.archi_sub.archi_sub.db.repository.user.UserFileRepository;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class DeleteFileJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    private final FileDao fileDao;
    private final AwsS3Service awsS3Service;
    private final FileRepository fileRepository;
    private final UserFileRepository userFileRepository;
    private final ContentFileRepository contentFileRepository;

    // 다중 DB일 경우 해당하는 타켓 entityManager를 가져와야한다.
    private final EntityManagerFactory entityManagerFactory;

    private final DeleteFileJobExecutionListener deleteFileJobExecutionListener;
    private final DeleteFileStepExceptionListener deleteFileStepExceptionListener;

    @Value("${chunkSize:500}")
    private int chunkSize;

    @Bean
    public Job deleteFileJob() {
        return new JobBuilder("deleteFileJob", jobRepository)
                .listener(deleteFileJobExecutionListener)
                .start(deleteUserFileStep())
                .next(deleteContentFileStep())
                .build();
    }

    // *******************************************
    // user file 삭제

    @Bean
    public Step deleteUserFileStep() {
        return new StepBuilder("deleteUserFileStep", jobRepository)
                .listener(deleteFileStepExceptionListener)
                .<UserFileEntity, UserFileEntity> chunk(chunkSize, transactionManager)
                .reader(deleteUserFileReader())
                .writer(deleteUserFileWriter())
//                .exceptionHandler()
//                .faultTolerant()  // 예외 발생 시에도 계속 진행하도록 설정
//                .skip(Exception.class) // 예외를 스킵하도록 설정 (선택 사항)
//                .skipLimit(5) // 최대 5번까지 예외를 스킵하도록 설정 (선택 사항)
                .build();
    }

    @Bean
    public JpaPagingItemReader<UserFileEntity> deleteUserFileReader() {
        return new JpaPagingItemReaderBuilder<UserFileEntity>()
                .name("JpaDeleteUserFileReader") // Item Reader 이름을 설정하여 로그나 예외상황에 필요
                .entityManagerFactory(entityManagerFactory)
                .pageSize(chunkSize)
                .queryString("SELECT u FROM UserFileEntity as u " +
                        "WHERE u.delYn = :delYn  ")
                .parameterValues(Collections.singletonMap("delYn", BooleanFlag.Y)) // 파라미터로 일주일 전 시간 설정
                .build();
    }

    @Bean
    public ItemWriter<UserFileEntity> deleteUserFileWriter() {
        return items -> {

            if(!items.isEmpty()){
                List<UserFileEntity> deleteUserFileEntityList = new ArrayList<>(items.getItems());

                List<Long> deleteUserFileIdEntityList = items.getItems().stream()
                        .map(UserFileEntity::getId)
                        .toList();

                // 파일하는 파일들 가져오고
                List<FileEntity> deleteFileEntityList = fileDao.findDeleteFileByUserFileIds(deleteUserFileIdEntityList);

                // file 지울때 연관관계 때매 userFile 먼저 삭제
                userFileRepository.deleteAllInBatch(deleteUserFileEntityList);

                List<String> deleteFilePathList = deleteFileEntityList.stream()
                        .map(FileEntity::getPath)
                        .toList();
                // 파일 aws 삭제
                awsS3Service.deleteFiles(deleteFilePathList);

                // 파일 삭제
                fileRepository.deleteAllInBatch(deleteFileEntityList);
            }
        };
    }

    //TODO 아니 JpaItemWriter걸 쓰니까 JpaPagingItemReader 갯수만큼 UserFileEntity를 select 조회하네..
    // 그래서 ItemWriter로 바꿈.
//    @Bean
//    public JpaItemWriter<UserFileEntity> deleteFileWriter() {
//        return new JpaItemWriterBuilder<UserFileEntity>()
//                .entityManagerFactory(entityManagerFactory)
//                .usePersist(false) //merge() 처리
//                .build();
//    }



    // *******************************************
    // content file 삭제

    @Bean
    public Step deleteContentFileStep() {
        return new StepBuilder("deleteContentFileStep", jobRepository)
                .listener(deleteFileStepExceptionListener)
                .<ContentFileEntity, ContentFileEntity> chunk(chunkSize, transactionManager)
                .reader(deleteContentFileReader())
                .writer(deleteContentFileWriter())
                .build();
    }

    @Bean
    public JpaPagingItemReader<ContentFileEntity> deleteContentFileReader() {
        return new JpaPagingItemReaderBuilder<ContentFileEntity>()
                .name("JpaDeleteContentFileReader") // Item Reader 이름을 설정하여 로그나 예외상황에 필요
                .entityManagerFactory(entityManagerFactory)
                .pageSize(chunkSize)
                .queryString("SELECT c FROM ContentFileEntity as c " +
                        "WHERE c.delYn = :delYn  ")
                .parameterValues(Collections.singletonMap("delYn", BooleanFlag.Y)) // 파라미터로 일주일 전 시간 설정
                .build();
    }

    @Bean
    public ItemWriter<ContentFileEntity> deleteContentFileWriter() {
        return items -> {

            if(!items.isEmpty()){
                List<ContentFileEntity> deleteContentFileEntityList = new ArrayList<>(items.getItems());

                List<Long> deleteContentFileIdEntityList = items.getItems().stream()
                        .map(ContentFileEntity::getId)
                        .toList();

                // 파일하는 파일들 가져오고
                List<FileEntity> deleteFileEntityList = fileDao.findDeleteFileByContentFileIds(deleteContentFileIdEntityList);

                // file 지울때 연관관계 때매 userFile 먼저 삭제
                contentFileRepository.deleteAllInBatch(deleteContentFileEntityList);

                List<String> deleteFilePathList = deleteFileEntityList.stream()
                        .map(FileEntity::getPath)
                        .toList();
                // 파일 aws 삭제
                awsS3Service.deleteFiles(deleteFilePathList);

                // 파일 삭제
                fileRepository.deleteAllInBatch(deleteFileEntityList);
            }
        };
    }

}
