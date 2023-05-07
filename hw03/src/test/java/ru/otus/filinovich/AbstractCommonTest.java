package ru.otus.filinovich;

import ru.otus.filinovich.domain.ListOfQuestion;
import ru.otus.filinovich.domain.Question;
import ru.otus.filinovich.domain.ResultTesting;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCommonTest {

    protected ListOfQuestion generateTestListOfQuestion() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question(
                "First question?", "1", new String[]{"1. 1", "2. 2", "3. 3", "4. 4", "5. 5"}));
        questions.add(new Question(
                "Second question?", "2", new String[]{"1. 6", "2. 2", "3. 9", "4. 0", "5. 11"}));
        questions.add(new Question(
                "Third question?", "3", new String[]{"1. one", "2. two", "3. four", "4. eight", "5. zero"}));
        questions.add(new Question(
                "Fourth question?", "4", new String[]{"1. alex", "2. jacob", "3. jasper", "4. robert", "5. nikolas"}));
        questions.add(new Question(
                "Fifth question?", "5", new String[]{"1. one", "2. two", "3. three", "4. four", "5. six"}));
        return new ListOfQuestion(questions);
    }

    protected ResultTesting generateTestResultTesting(ListOfQuestion listOfQuestion) {
        ResultTesting resultTesting = new ResultTesting();
        listOfQuestion.getQuestions().forEach(question -> {
            resultTesting.getAnswersList().put(question, question.getCorrectAnswer());
        });
        return resultTesting;
    }

    protected ResultTesting generateTestResultTesting() {
        ListOfQuestion listOfQuestion = generateTestListOfQuestion();
        ResultTesting resultTesting = new ResultTesting();
        listOfQuestion.getQuestions().forEach(question -> {
            resultTesting.getAnswersList().put(question, question.getCorrectAnswer());
        });
        return resultTesting;
    }
}
