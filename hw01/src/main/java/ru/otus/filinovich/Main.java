package ru.otus.filinovich;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.filinovich.domain.ListOfQuestion;
import ru.otus.filinovich.service.QuestionService;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        var questionService = context.getBean(QuestionService.class);

        ListOfQuestion questions = questionService.getQuestions();
        System.out.println(questions);
    }
}
