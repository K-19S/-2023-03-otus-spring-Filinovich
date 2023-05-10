package ru.otus.filinovich.service.testing;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.filinovich.configs.TestingProps;
import ru.otus.filinovich.domain.Question;
import ru.otus.filinovich.domain.ResultTesting;
import ru.otus.filinovich.service.io.IoService;
import ru.otus.filinovich.service.question.QuestionService;
import ru.otus.filinovich.service.user.UserService;
import ru.otus.filinovich.utils.PropertiesProvider;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class StudentTestingService implements TestingService {

    private final QuestionService questionService;

    private final UserService userService;

    private final PropertiesProvider propertiesProvider;

    private final TestingProps testingProps;

    private final IoService ioService;

    @Override
    public void startTest() {
        userService.askUserSurnameAndName();
        ResultTesting resultTesting = questionService.askQuestions();
        analyzeResultAndPrint(resultTesting);
    }

    private void analyzeResultAndPrint(ResultTesting resultTesting) {
        int correctAnswers = analyzeAndGetNumberOfCorrectAnswers(resultTesting);
        printResultTesting(correctAnswers);
    }

    private int analyzeAndGetNumberOfCorrectAnswers(ResultTesting resultTesting) {
        int numberOfCorrectAnswers = 0;
        Map<Question, String> answerList = resultTesting.getAnswersList();
        for (Question question : answerList.keySet()) {
            if (question.getCorrectAnswer().equals(answerList.get(question))) {
                numberOfCorrectAnswers++;
            }
        }
        return numberOfCorrectAnswers;
    }

    private void printResultTesting(Integer correctAnswers) {
        if (correctAnswers >= testingProps.getRequiredNumberOfRightAnswers()) {
            var passedMessage = propertiesProvider.getMessage(
                    "testing.passed_message", userService.getUser().toString());
            ioService.outputString(passedMessage);
        } else {
            var failedMessage = propertiesProvider.getMessage(
                    "testing.failed_message", userService.getUser().toString());
            ioService.outputString(failedMessage);
        }
        var scoringMessage = propertiesProvider.getMessage(
                "testing.scoring_message", testingProps.getRequiredNumberOfRightAnswers(), correctAnswers);
        ioService.outputString(scoringMessage);
    }
}