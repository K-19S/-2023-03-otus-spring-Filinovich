package ru.otus.filinovich.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class CleanLibraryStepConfig {

    private final MongoTemplate mongoTemplate;

    private final JobRepository jobRepository;

    private final PlatformTransactionManager platformTransactionManager;

    @Bean
    public Step cleanLibraryStep() {
        return new StepBuilder("cleanLibraryStep", jobRepository)
            .tasklet(cleanLibraryTasklet(), platformTransactionManager)
            .build();
    }

    @Bean
    public MethodInvokingTaskletAdapter cleanLibraryTasklet() {
        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();
        adapter.setTargetObject(this);
        adapter.setTargetMethod("cleanLibrary");

        return adapter;
    }

    public void cleanLibrary() throws Exception {
        mongoTemplate.dropCollection("bookComments");
        mongoTemplate.dropCollection("books");
        mongoTemplate.dropCollection("authors");
        mongoTemplate.dropCollection("genres");
    }
}
