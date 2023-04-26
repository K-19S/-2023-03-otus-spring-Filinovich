package ru.otus.filinovich.service.testing;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.filinovich.domain.Question;
import ru.otus.filinovich.domain.ResultTesting;
import ru.otus.filinovich.service.question.QuestionService;
import ru.otus.filinovich.service.user.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StudentTestingService implements TestingService {

    private final QuestionService questionService;

    private final UserService userService;

    @Value("${testing.required_number_of_right_answers}")
    private int requiredNumberOfRightAnswers;

    @Value("${testing.passed_message}")
    private String passedMessage;

    @Value("${testing.failed_message}")
    private String failedMessage;

    @Value("${testing.scoring_message}")
    private String scoringMessage;

    @Value("${testing.error_message}")
    private String errorMessage;

    @Override
    public void startTest() {
        try (BufferedReader reader = getConsoleReader()) {
            userService.askUserSurnameAndName(reader);
            ResultTesting resultTesting = questionService.askQuestions(reader);
            analyzeResultAndPrint(resultTesting);
        } catch (IOException e) {
            System.out.println(errorMessage);
        }
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

    private void printResultTesting(int correctAnswers) {
        if (correctAnswers >= requiredNumberOfRightAnswers) {
            System.out.printf(passedMessage + "\n", userService.getUser().toString());
        } else {
            System.out.printf(failedMessage + "\n", userService.getUser().toString());
        }
        System.out.printf(scoringMessage, requiredNumberOfRightAnswers, correctAnswers);
    }
}