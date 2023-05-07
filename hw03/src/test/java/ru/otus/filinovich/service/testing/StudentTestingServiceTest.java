package ru.otus.filinovich.service.testing;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.filinovich.AbstractCommonTest;
import ru.otus.filinovich.configs.AppProps;
import ru.otus.filinovich.domain.User;
import ru.otus.filinovich.service.io.IoService;
import ru.otus.filinovich.service.question.QuestionService;
import ru.otus.filinovich.service.user.UserService;

import java.util.Locale;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@RequiredArgsConstructor
@RunWith(SpringRunner.class)
class StudentTestingServiceTest extends AbstractCommonTest {

    @Autowired
    @InjectMocks
    private StudentTestingService testingService;

    @MockBean
    private QuestionService questionService;

    @MockBean
    private UserService userService;

    @MockBean
    private IoService ioService;

    @MockBean
    private AppProps appProps;

    @Test
    void studentImplCsvTest() {
        when(questionService.askQuestions()).thenReturn(generateTestResultTesting());
        when(appProps.getRequiredNumberOfRightAnswers()).thenReturn(4);
        when(appProps.getLocale()).thenReturn(new Locale("en"));
        User testUser = new User();
        testUser.setName("TestName");
        testUser.setSurname("TestSurname");
        when(userService.getUser()).thenReturn(testUser);
        testingService.startTest();

        verify(ioService, times(1)).outputString(
                "TEST Congratulations, TestName TestSurname! You passed out test!");
        verify(ioService, times(1)).outputString(
                "TEST You need 4 correct answers to pass the test. you scored 5");
    }
}