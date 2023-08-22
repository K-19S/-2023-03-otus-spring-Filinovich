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
import ru.otus.filinovich.converter.BookJpaToMongoConverter;
import ru.otus.filinovich.domain.jpa.JpaBook;
import ru.otus.filinovich.domain.mongo.MongoBook;

import java.util.List;

import static ru.otus.filinovich.config.JobConfig.CHUNK_SIZE;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class BookStepConfig {

    private final JobRepository jobRepository;

    private final EntityManagerFactory entityManagerFactory;

    private final PlatformTransactionManager platformTransactionManager;

    @Bean
    public Step bookStep(ItemReader<JpaBook> bookReader, ItemWriter<MongoBook> bookWriter,
                           ItemProcessor<JpaBook, MongoBook> bookItemProcessor) {
        return new StepBuilder("bookStep", jobRepository)
            .<JpaBook, MongoBook>chunk(CHUNK_SIZE, platformTransactionManager)
            .reader(bookReader)
            .processor(bookItemProcessor)
            .writer(bookWriter)
            .listener(itemReadListener())
            .listener(itemWriteListener())
            .listener(itemProcessListener())
            .listener(chunkListener())
            .build();
    }

    @Bean
    public JpaCursorItemReader<JpaBook> bookReader() {
        return new JpaCursorItemReaderBuilder<JpaBook>()
            .name("bookItemReader")
            .queryString("from JpaBook")
            .entityManagerFactory(entityManagerFactory)
            .build();
    }

    @Bean
    public ItemProcessor<JpaBook, MongoBook> bookItemProcessor(BookJpaToMongoConverter converter) {
        return converter::convert;
    }

    @Bean
    public MongoItemWriter<MongoBook> bookWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<MongoBook>()
            .template(mongoTemplate)
            .build();
    }

    private ItemReadListener<JpaBook> itemReadListener() {
        return new ItemReadListener<>() {
            public void beforeRead() {
                log.info("Начало чтения книги");
            }

            public void afterRead(@NonNull JpaBook o) {
                log.info("Успешно прочитана книга с ID: " + o.getId());
            }

            public void onReadError(@NonNull Exception e) {
                log.info("Ошибка чтения книги");
            }
        };
    }

    private ItemWriteListener<MongoBook> itemWriteListener() {
        return new ItemWriteListener<MongoBook>() {
            public void beforeWrite(@NonNull List<MongoBook> list) {
                log.info("Начало записи пакета книг");
            }

            public void afterWrite(@NonNull List<MongoBook> list) {
                log.info("Конец записи пакета книг");
            }

            public void onWriteError(@NonNull Exception e, @NonNull List<MongoBook> list) {
                log.info("Ошибка записи книг");
            }
        };
    }

    private ItemProcessListener<JpaBook, MongoBook> itemProcessListener() {
        return new ItemProcessListener<>() {
            public void beforeProcess(@NonNull JpaBook o) {
                log.info("Начало обработки");
            }

            public void afterProcess(@NonNull JpaBook o, MongoBook o2) {
                log.info("Конец обработки");
            }

            public void onProcessError(@NonNull JpaBook o, @NonNull Exception e) {
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
