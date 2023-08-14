package ru.otus.filinovich.config;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.lang.NonNull;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.filinovich.converter.BookCommentJpaToMongoConverter;
import ru.otus.filinovich.domain.jpa.JpaBookComment;
import ru.otus.filinovich.domain.mongo.MongoBookComment;

import java.util.List;

import static ru.otus.filinovich.config.JobConfig.CHUNK_SIZE;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class BookCommentStepConfig {

    private final JobRepository jobRepository;

    private final EntityManagerFactory entityManagerFactory;

    private final PlatformTransactionManager platformTransactionManager;

    @Bean
    public Step commentStep(ItemReader<JpaBookComment> commentReader, ItemWriter<MongoBookComment> commentWriter,
                         ItemProcessor<JpaBookComment, MongoBookComment> commentItemProcessor) {
        return new StepBuilder("commentStep", jobRepository)
            .<JpaBookComment, MongoBookComment>chunk(CHUNK_SIZE, platformTransactionManager)
            .reader(commentReader)
            .processor(commentItemProcessor)
            .writer(commentWriter)
            .listener(itemReadListener())
            .listener(itemWriteListener())
            .listener(itemProcessListener())
            .listener(chunkListener())
            .build();
    }

    @Bean
    public JpaCursorItemReader<JpaBookComment> commentReader() {
        return new JpaCursorItemReaderBuilder<JpaBookComment>()
            .name("commentItemReader")
            .queryString("from JpaBookComment")
            .entityManagerFactory(entityManagerFactory)
            .build();
    }

    @Bean
    public ItemProcessor<JpaBookComment, MongoBookComment> commentItemProcessor(
            BookCommentJpaToMongoConverter converter) {
        return converter::convert;
    }

    @Bean
    public MongoItemWriter<MongoBookComment> commentWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<MongoBookComment>()
            .template(mongoTemplate)
            .build();
    }

    private ItemReadListener<JpaBookComment> itemReadListener() {
        return new ItemReadListener<>() {
            public void beforeRead() {
                log.info("Начало чтения комментария");
            }

            public void afterRead(@NonNull JpaBookComment o) {
                log.info("Успешно прочитан комментарий с ID: " + o.getId());
            }

            public void onReadError(@NonNull Exception e) {
                log.info("Ошибка чтения комментария");
            }
        };
    }

    private ItemWriteListener<MongoBookComment> itemWriteListener() {
        return new ItemWriteListener<MongoBookComment>() {
            public void beforeWrite(@NonNull List<MongoBookComment> list) {
                log.info("Начало записи пакета комментариев");
            }

            public void afterWrite(@NonNull List<MongoBookComment> list) {
                log.info("Конец записи пакета комментариев");
            }

            public void onWriteError(@NonNull Exception e, @NonNull List<MongoBookComment> list) {
                log.info("Ошибка записи комментариев");
            }
        };
    }

    private ItemProcessListener<JpaBookComment, MongoBookComment> itemProcessListener() {
        return new ItemProcessListener<>() {
            public void beforeProcess(@NonNull JpaBookComment o) {
                log.info("Начало обработки");
            }

            public void afterProcess(@NonNull JpaBookComment o, MongoBookComment o2) {
                log.info("Конец обработки");
            }

            public void onProcessError(@NonNull JpaBookComment o, @NonNull Exception e) {
                log.info("Ошибка обработки");
            }
        };
    }

    private ChunkListener chunkListener() {
        return new ChunkListener() {
            public void beforeChunk(@NonNull ChunkContext chunkContext) {
                log.info("Начало пачки");
            }

            public void afterChunk(@NonNull ChunkContext chunkContext) {
                log.info("Конец пачки");
            }

            public void afterChunkError(@NonNull ChunkContext chunkContext) {
                log.info("Ошибка пачки");
            }
        };
    }
}
