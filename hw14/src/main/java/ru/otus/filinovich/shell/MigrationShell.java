package ru.otus.filinovich.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Properties;
import java.util.UUID;

import static ru.otus.filinovich.config.JobConfig.IMPORT_USER_JOB_NAME;

@ShellComponent
@RequiredArgsConstructor
public class MigrationShell {

    private final JobOperator jobOperator;

    private final JobExplorer jobExplorer;

    @ShellMethod(value = "startMigrationJobWithJobOperator", key = "sm")
    public void startMigrationJobWithJobOperator() throws Exception {
        Properties properties = new Properties();
        properties.put("4restart", UUID.randomUUID().toString());
        Long executionId = jobOperator.start(IMPORT_USER_JOB_NAME, properties);
        System.out.println(jobOperator.getSummary(executionId));
    }

    @ShellMethod(value = "showInfo", key = "i")
    public void showInfo() {
        System.out.println(jobExplorer.getJobNames());
        System.out.println(jobExplorer.getLastJobInstance(IMPORT_USER_JOB_NAME));
    }
}
