package ru.otus.filinovich.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JobConfig {

    public static final String IMPORT_USER_JOB_NAME = "migrationLibraryFromJpaToMongoJob";

    public static final int CHUNK_SIZE = 5;

    private final JobRepository jobRepository;

    @Bean
    public Job migrationLibraryFromJpaToMongoJob(
            Step genreStep, Step authorStep, Step bookStep, Step commentStep, Step cleanLibraryStep) {
        return new JobBuilder(IMPORT_USER_JOB_NAME, jobRepository)
            .incrementer(new RunIdIncrementer())
            .flow(cleanLibraryStep)
            .next(genreStep)
            .next(authorStep)
            .next(bookStep)
            .next(commentStep)
            .end()
            .listener(new JobExecutionListener() {
                @Override
                public void beforeJob(@NonNull JobExecution jobExecution) {
                    log.info("Начало миграции библиотеки");
                }

                @Override
                public void afterJob(@NonNull JobExecution jobExecution) {
                    log.info("Конец миграции библиотеки");
                }
            })
            .build();
    }


}
