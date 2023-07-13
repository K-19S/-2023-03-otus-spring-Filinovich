package ru.otus.filinovich.service.testing;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import ru.otus.filinovich.dao.QuestionDao;
import ru.otus.filinovich.dao.QuestionDaoCsv;
import ru.otus.filinovich.service.question.QuestionService;
import ru.otus.filinovich.service.question.QuestionServiceImpl;
import ru.otus.filinovich.service.user.UserService;
import ru.otus.filinovich.service.user.UserServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

class StudentTestingServiceTest {

    private static TestingService testingService;

    @BeforeAll
    static void setUp() {
        QuestionDao questionDao = new QuestionDaoCsv();
        ReflectionTestUtils.setField(questionDao, "csvFileName", "test.csv");
        QuestionService questionService = new QuestionServiceImpl(questionDao);
        UserService userService = new UserServiceImpl();
        testingService = spy(new StudentTestingService(questionService, userService));
        ReflectionTestUtils.setField(testingService, "requiredNumberOfRightAnswers", 4);
        ReflectionTestUtils.setField(testingService,
                "passedMessage", "Congratulations, %s! You passed out test!");
        ReflectionTestUtils.setField(testingService,
                "failedMessage", "Sorry, %s, you didn't pass the test.");
        ReflectionTestUtils.setField(testingService,
                "scoringMessage", "You need %d correct answers to pass the test. you scored %d");
        ReflectionTestUtils.setField(testingService,
                "errorMessage", "Sorry. Something went wrong");
    }

    @Test
    void studentImplCsvTest() throws IOException {
        try (BufferedReader mock = mock(BufferedReader.class)) {
            when(mock.readLine()).thenReturn("TestSurname", "TestName");
            when(testingService.getConsoleReader()).thenReturn(mock);
            testingService.startTest();

            verify(mock, times(7)).readLine();
            verify(testingService, times(1)).getConsoleReader();
        }
    }

}