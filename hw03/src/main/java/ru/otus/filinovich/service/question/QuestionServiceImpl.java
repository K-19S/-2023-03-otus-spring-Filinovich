package ru.otus.filinovich.service.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.filinovich.dao.QuestionDao;
import ru.otus.filinovich.domain.ListOfQuestion;
import ru.otus.filinovich.domain.Question;
import ru.otus.filinovich.domain.ResultTesting;
import ru.otus.filinovich.service.io.IoService;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final IoService ioService;

    private final QuestionDao questionDao;

    @Override
    public ListOfQuestion getQuestions() {
        return questionDao.getQuestions();
    }

    @Override
    public ResultTesting askQuestions() {
        ResultTesting resultTesting = new ResultTesting();
        ListOfQuestion listOfQuestion = getQuestions();
        for (Question question : listOfQuestion.getQuestions()) {
            String answer = ioService.readStringWithPromt(question.toString());
            resultTesting.getAnswersList().put(question, answer);
        }
        return resultTesting;
    }
}
