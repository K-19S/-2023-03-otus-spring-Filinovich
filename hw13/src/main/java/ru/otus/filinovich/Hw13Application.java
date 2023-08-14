package ru.otus.filinovich;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "ru.otus.filinovich.dao")
public class Hw13Application {

    public static void main(String[] args) {
        SpringApplication.run(Hw13Application.class, args);
    }
}
