package com.archi_sub.archi_sub.config.batch;

import com.archi_sub.archi_sub.config.batch.listener.BestContentJobExecutionListener;
import com.archi_sub.archi_sub.config.batch.listener.BestContentStepExceptionListener;
import com.archi_sub.archi_sub.db.entity.content.BestContentEntity;
import com.archi_sub.archi_sub.db.entity.content.ContentEntity;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class BestContentJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    // 다중 DB일 경우 해당하는 타켓 entityManager를 가져와야한다.
    private final EntityManagerFactory entityManagerFactory;
//    @Autowired
//    @Qualifier("primaryEntityManagerFactory")
//    private EntityManagerFactory primaryEntityManagerFactory;

    private final BestContentJobExecutionListener bestContentJobExecutionListener;
    private final BestContentStepExceptionListener bestContentStepExceptionListener;

    @Value("${chunkSize:10}")
    private int chunkSize;

    @Bean
    public Job bestContentJob() {
        return new JobBuilder("bestContentJob", jobRepository)
                .listener(bestContentJobExecutionListener)
                .start(bestContentStep())
                .build();
    }

    @Bean
    public Step bestContentStep() {
        return new StepBuilder("bestContentStep", jobRepository)
                .listener(bestContentStepExceptionListener)
                .<ContentEntity, BestContentEntity> chunk(chunkSize, transactionManager)
                .reader(bestContentReader())
                .processor(bestContentProcessor())
                .writer(bestContentWriter())
//                .exceptionHandler()
//                .faultTolerant()  // 예외 발생 시에도 계속 진행하도록 설정
//                .skip(Exception.class) // 예외를 스킵하도록 설정 (선택 사항)
//                .skipLimit(5) // 최대 5번까지 예외를 스킵하도록 설정 (선택 사항)
                .build();
    }

    @Bean
    public JpaPagingItemReader<ContentEntity> bestContentReader() {
        return new JpaPagingItemReaderBuilder<ContentEntity>()
                // 만약 배치 작업 중 문제가 발생했을 때, 에러 로그에 "JpaPushMsgItemReader"라는 이름이 포함되면 어떤 리더(ItemReader)에서 문제가 발생했는지 쉽게 알 수 있습니다.
                // 또한, 스프링 배치의 모니터링 툴을 사용할 때 각 리더의 이름을 설정해 두면 어떤 리더가 어떤 데이터를 처리하고 있는지 확인할 수 있습니다.
                .name("JpaBestContentReader") // Item Reader 이름을 설정하여 로그나 예외상황에 필요
                .entityManagerFactory(entityManagerFactory)
                .pageSize(chunkSize)
                .queryString("SELECT c FROM ContentEntity as c " +
                        "JOIN c.contentLikes cl " +
                        "WHERE c.delYn = 'N' " +
                        "AND c.createdAt >= :oneWeekAgo " + // 일주일 이내의 게시글만 조회
                        "GROUP BY c " +
                        "ORDER BY COUNT(cl) DESC")
                .parameterValues(Collections.singletonMap("oneWeekAgo", LocalDateTime.now().minusWeeks(1))) // 파라미터로 일주일 전 시간 설정
                .build();
    }

    @Bean
    public ItemProcessor<ContentEntity, BestContentEntity> bestContentProcessor() {
        AtomicInteger index = new AtomicInteger(1); // 인덱스를 관리하는 AtomicInteger
        return (content) -> {
            int currentIndex = index.getAndIncrement(); // 현재 인덱스를 가져오고 증가시킴
            return BestContentEntity.builder()
                    .content(content)
                    .contentRank(currentIndex)
                    .build();
        };
    }

    @Bean
    public JpaItemWriter<BestContentEntity> bestContentWriter() {
        return new JpaItemWriterBuilder<BestContentEntity>()
                .entityManagerFactory(entityManagerFactory)
                .usePersist(false) //merge() 처리
                .build();
    }
}
