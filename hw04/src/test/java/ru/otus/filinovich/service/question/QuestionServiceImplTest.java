package ru.otus.filinovich.service.question;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.filinovich.AbstractCommonTest;
import ru.otus.filinovich.dao.QuestionDao;
import ru.otus.filinovich.domain.ListOfQuestion;
import ru.otus.filinovich.domain.Question;
import ru.otus.filinovich.domain.ResultTesting;
import ru.otus.filinovich.service.io.IoService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;

@SpringBootTest
@ActiveProfiles("test")
public class QuestionServiceImplTest extends AbstractCommonTest {

    @MockBean
    private QuestionDao questionDao;

    @MockBean
    private IoService ioService;

    @Autowired
    private QuestionServiceImpl questionServiceImpl;

    @Test
    void getQuestionsShouldReturnListOfQuestions() {
        ListOfQuestion actual = generateTestListOfQuestion();
        when(questionDao.getQuestions()).thenReturn(actual);
        ListOfQuestion expected = questionServiceImpl.getQuestions();

        assertThat(expected).isEqualTo(actual);
        verify(questionDao).getQuestions();
    }

    @Test
    void askQuestionsCorrectAnswers() {
        ListOfQuestion listOfQuestion = generateTestListOfQuestion();
        ResultTesting actual = generateTestResultTesting(listOfQuestion);
        when(questionDao.getQuestions()).thenReturn(listOfQuestion);
        when(ioService.readStringWithPromt(any())).thenReturn("1", "2", "3", "4", "5");
        ResultTesting result = questionServiceImpl.askQuestions();

        assertThat(actual).isEqualTo(result);
        verify(questionDao, times(1)).getQuestions();
        verify(ioService, times(5)).readStringWithPromt(any());
    }

    @Test
    void askQuestionsUncorrectanswers() {
        ListOfQuestion listOfQuestion = generateTestListOfQuestion();
        when(questionDao.getQuestions()).thenReturn(listOfQuestion);
        when(ioService.readStringWithPromt(any())).thenReturn("1");
        ResultTesting result = questionServiceImpl.askQuestions();

        ResultTesting actual = new ResultTesting();
        for (Question question : listOfQuestion.getQuestions()) {
            actual.getAnswersList().put(question, "1");
        }

        assertThat(actual).isEqualTo(result);
        verify(ioService, times(5)).readStringWithPromt(any());
        verify(questionDao, times(1)).getQuestions();
    }
}