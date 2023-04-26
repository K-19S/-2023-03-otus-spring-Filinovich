package ru.otus.filinovich;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.filinovich.service.testing.TestingService;

@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
public class Application {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context
                = new AnnotationConfigApplicationContext(Application.class);

        var questionService = context.getBean(TestingService.class);

        questionService.startTest();
    }
}
