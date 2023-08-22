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
import ru.otus.filinovich.converter.AuthorJpaToMongoConverter;
import ru.otus.filinovich.domain.jpa.JpaAuthor;
import ru.otus.filinovich.domain.mongo.MongoAuthor;

import java.util.List;

import static ru.otus.filinovich.config.JobConfig.CHUNK_SIZE;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AuthorStepConfig {

    private final JobRepository jobRepository;

    private final EntityManagerFactory entityManagerFactory;

    private final PlatformTransactionManager platformTransactionManager;

    @Bean
    public Step authorStep(ItemReader<JpaAuthor> authorReader, ItemWriter<MongoAuthor> authorWriter,
                           ItemProcessor<JpaAuthor, MongoAuthor> authorItemProcessor) {
        return new StepBuilder("authorStep", jobRepository)
            .<JpaAuthor, MongoAuthor>chunk(CHUNK_SIZE, platformTransactionManager)
            .reader(authorReader)
            .processor(authorItemProcessor)
            .writer(authorWriter)
            .listener(itemReadListener())
            .listener(itemWriteListener())
            .listener(itemProcessListener())
            .listener(chunkListener())
            .build();
    }

    @Bean
    public JpaCursorItemReader<JpaAuthor> authorReader() {
        return new JpaCursorItemReaderBuilder<JpaAuthor>()
            .name("authorItemReader")
            .queryString("from JpaAuthor")
            .entityManagerFactory(entityManagerFactory)
            .build();
    }

    @Bean
    public ItemProcessor<JpaAuthor, MongoAuthor> authorItemProcessor(AuthorJpaToMongoConverter converter) {
        return converter::convert;
    }

    @Bean
    public MongoItemWriter<MongoAuthor> authorWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<MongoAuthor>()
            .template(mongoTemplate)
            .build();
    }

    private ItemReadListener<JpaAuthor> itemReadListener() {
        return new ItemReadListener<>() {
            public void beforeRead() {
                log.info("Начало чтения автора");
            }

            public void afterRead(@NonNull JpaAuthor o) {
                log.info("Успешно прочитан автор с ID: " + o.getId());
            }

            public void onReadError(@NonNull Exception e) {
                log.info("Ошибка чтения автора");
            }
        };
    }

    private ItemWriteListener<MongoAuthor> itemWriteListener() {
        return new ItemWriteListener<MongoAuthor>() {
            public void beforeWrite(@NonNull List<MongoAuthor> list) {
                log.info("Начало записи пакета авторов");
            }

            public void afterWrite(@NonNull List<MongoAuthor> list) {
                log.info("Конец записи пакета авторов");
            }

            public void onWriteError(@NonNull Exception e, @NonNull List<MongoAuthor> list) {
                log.info("Ошибка записи авторов");
            }
        };
    }

    private ItemProcessListener<JpaAuthor, MongoAuthor> itemProcessListener() {
        return new ItemProcessListener<>() {
            public void beforeProcess(@NonNull JpaAuthor o) {
                log.info("Начало обработки");
            }

            public void afterProcess(@NonNull JpaAuthor o, MongoAuthor o2) {
                log.info("Конец обработки");
            }

            public void onProcessError(@NonNull JpaAuthor o, @NonNull Exception e) {
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
