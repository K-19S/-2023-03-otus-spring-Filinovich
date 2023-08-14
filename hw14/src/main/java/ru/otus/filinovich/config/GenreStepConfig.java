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
import ru.otus.filinovich.converter.GenreJpaToMongoConverter;
import ru.otus.filinovich.domain.jpa.JpaGenre;
import ru.otus.filinovich.domain.mongo.MongoGenre;

import java.util.List;

import static ru.otus.filinovich.config.JobConfig.CHUNK_SIZE;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class GenreStepConfig {

    private final JobRepository jobRepository;

    private final EntityManagerFactory entityManagerFactory;

    private final PlatformTransactionManager platformTransactionManager;

    @Bean
    public Step genreStep(ItemReader<JpaGenre> genreReader, ItemWriter<MongoGenre> genreWriter,
                          ItemProcessor<JpaGenre, MongoGenre> genreItemProcessor) {
        return new StepBuilder("genreStep", jobRepository)
            .<JpaGenre, MongoGenre>chunk(CHUNK_SIZE, platformTransactionManager)
            .reader(genreReader)
            .processor(genreItemProcessor)
            .writer(genreWriter)
            .listener(itemReadListener())
            .listener(itemWriteListener())
            .listener(itemProcessListener())
            .listener(chunkListener())
            .build();
    }

    @Bean
    public JpaCursorItemReader<JpaGenre> genreReader() {
        return new JpaCursorItemReaderBuilder<JpaGenre>()
            .name("genreItemReader")
            .queryString("from JpaGenre")
            .entityManagerFactory(entityManagerFactory)
            .build();
    }

    @Bean
    public ItemProcessor<JpaGenre, MongoGenre> genreProcessor(GenreJpaToMongoConverter converter) {
        return converter::convert;
    }

    @Bean
    public MongoItemWriter<MongoGenre> genreWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<MongoGenre>()
            .template(mongoTemplate)
            .build();
    }

    private ItemReadListener<JpaGenre> itemReadListener() {
        return new ItemReadListener<>() {
            public void beforeRead() {
                log.info("Начало чтения жанра");
            }

            public void afterRead(@NonNull JpaGenre o) {
                log.info("Успешно прочитан жанр с ID: " + o.getId());
            }

            public void onReadError(@NonNull Exception e) {
                log.info("Ошибка чтения жанра");
            }
        };
    }

    private ItemWriteListener<MongoGenre> itemWriteListener() {
        return new ItemWriteListener<MongoGenre>() {
            public void beforeWrite(@NonNull List<MongoGenre> list) {
                log.info("Начало записи пакета жанров");
            }

            public void afterWrite(@NonNull List<MongoGenre> list) {
                log.info("Конец записи пакета жанров");
            }

            public void onWriteError(@NonNull Exception e, @NonNull List<MongoGenre> list) {
                log.info("Ошибка записи жанров");
            }
        };
    }

    private ItemProcessListener<JpaGenre, MongoGenre> itemProcessListener() {
        return new ItemProcessListener<>() {
            public void beforeProcess(@NonNull JpaGenre o) {
                log.info("Начало обработки");
            }

            public void afterProcess(@NonNull JpaGenre o, MongoGenre o2) {
                log.info("Конец обработки");
            }

            public void onProcessError(@NonNull JpaGenre o, @NonNull Exception e) {
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
