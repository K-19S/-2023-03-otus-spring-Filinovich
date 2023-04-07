package ru.otus.filinovich.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.filinovich.dao.QuestionDao;
import ru.otus.filinovich.dao.QuestionDaoCsv;
import ru.otus.filinovich.domain.ListOfQuestion;
import ru.otus.filinovich.domain.Question;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс QuestionServiceImpl")
class QuestionServiceImplTest {

    private static QuestionService questionService;

    @BeforeAll
    static void setUp() {
        QuestionDao questionDao = new QuestionDaoCsv("test.csv");
        questionService = new QuestionServiceImpl(questionDao);
    }

    @Test
    @DisplayName("должен выводить на экран текст вопросов с вариантами ответов")
    void test() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("First question?", new String[]{"1. yes", "2. no"}));
        questions.add(new Question("Second question?", new String[]{"1. good", "2. bad"}));
        ListOfQuestion actual = new ListOfQuestion(questions);

        ListOfQuestion expected = questionService.getQuestions();

        assertThat(actual).isEqualTo(expected);
    }
}