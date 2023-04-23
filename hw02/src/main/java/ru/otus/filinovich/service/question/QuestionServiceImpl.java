package ru.otus.filinovich.service.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.filinovich.dao.QuestionDao;
import ru.otus.filinovich.domain.ListOfQuestion;
import ru.otus.filinovich.domain.Question;
import ru.otus.filinovich.domain.ResultTesting;

import java.io.BufferedReader;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao questionDao;

    @Override
    public ListOfQuestion getQuestions() {
        return questionDao.getQuestions();
    }

    public ResultTesting askQuestions(BufferedReader reader) throws IOException {
        ResultTesting resultTesting = new ResultTesting();
        ListOfQuestion listOfQuestion = getQuestions();
        for (Question question : listOfQuestion.getQuestions()) {
            System.out.println(question);
            String answer = reader.readLine();
            resultTesting.getAnswersList().put(question, answer);
        }
        return resultTesting;
    }
}
