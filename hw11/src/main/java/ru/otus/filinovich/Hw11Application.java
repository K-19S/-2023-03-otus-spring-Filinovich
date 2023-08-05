package ru.otus.filinovich;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;


@EnableMongock
@SpringBootApplication
@EnableReactiveMongoRepositories(basePackages = "ru.otus.filinovich.dao")
public class Hw11Application {

    public static void main(String[] args) {
        SpringApplication.run(Hw11Application.class, args);
    }

}
