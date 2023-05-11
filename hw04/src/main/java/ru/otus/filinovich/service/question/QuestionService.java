package ru.otus.filinovich.service.question;

import ru.otus.filinovich.domain.ListOfQuestion;
import ru.otus.filinovich.domain.ResultTesting;

public interface QuestionService {

    ListOfQuestion getQuestions();

    ResultTesting askQuestions();
}
