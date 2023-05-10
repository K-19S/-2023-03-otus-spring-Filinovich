package ru.otus.filinovich.service.question;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class QuestionServiceImplTest extends AbstractCommonTest {

    @Mock
    private QuestionDao questionDao;

    @Mock
    private IoService ioService;

    @InjectMocks
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