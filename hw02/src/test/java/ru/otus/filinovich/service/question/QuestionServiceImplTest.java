package ru.otus.filinovich.service.question;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.filinovich.AbstractCommonTest;
import ru.otus.filinovich.dao.QuestionDao;
import ru.otus.filinovich.domain.ListOfQuestion;
import ru.otus.filinovich.domain.Question;
import ru.otus.filinovich.domain.ResultTesting;

import java.io.BufferedReader;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceImplTest extends AbstractCommonTest {

    @Mock
    private QuestionDao questionDao;

    private QuestionService questionService;

    @BeforeEach
    void startUp() {
        questionService = new QuestionServiceImpl(questionDao);
    }

    @Test
    void getQuestionsShouldReturnListOfQuestions() {
        ListOfQuestion actual = generateTestListOfQuestion();
        when(questionDao.getQuestions()).thenReturn(actual);
        ListOfQuestion expected = questionService.getQuestions();

        assertThat(expected).isEqualTo(actual);
        verify(questionDao).getQuestions();
    }

    @Test
    void askQuestionsCorrectAnswers() throws IOException {
        ListOfQuestion listOfQuestion = generateTestListOfQuestion();
        ResultTesting actual = generateTestResultTesting(listOfQuestion);
        when(questionDao.getQuestions()).thenReturn(listOfQuestion);
        BufferedReader mock = mock(BufferedReader.class);
        when(mock.readLine()).thenReturn("1", "2", "3", "4", "5");
        ResultTesting result = questionService.askQuestions(mock);

        assertThat(actual).isEqualTo(result);
        verify(mock, times(5)).readLine();
    }

    @Test
    void askQuestionsUncorrectanswers() throws IOException {
        ListOfQuestion listOfQuestion = generateTestListOfQuestion();
        when(questionDao.getQuestions()).thenReturn(listOfQuestion);
        BufferedReader mock = mock(BufferedReader.class);
        when(mock.readLine()).thenReturn("1");
        ResultTesting result = questionService.askQuestions(mock);

        ResultTesting actual = new ResultTesting();
        for (Question question : listOfQuestion.getQuestions()) {
            actual.getAnswersList().put(question, "1");
        }

        assertThat(actual).isEqualTo(result);
        verify(mock, times(5)).readLine();
        verify(questionDao, times(1)).getQuestions();
    }

    private ResultTesting generateTestResultTesting(ListOfQuestion listOfQuestion) {
        ResultTesting resultTesting = new ResultTesting();
        listOfQuestion.getQuestions().forEach(question -> {
            resultTesting.getAnswersList().put(question, question.getCorrectAnswer());
        });
        return resultTesting;
    }

}