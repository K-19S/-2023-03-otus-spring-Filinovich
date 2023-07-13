package ru.otus.filinovich.service;

import ru.otus.filinovich.dao.QuestionDao;
import ru.otus.filinovich.domain.ListOfQuestion;

public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao questionDao;

    public QuestionServiceImpl(QuestionDao dao) {
        this.questionDao = dao;
    }

    @Override
    public ListOfQuestion getQuestions() {
        return questionDao.getQuestions();
    }
}
