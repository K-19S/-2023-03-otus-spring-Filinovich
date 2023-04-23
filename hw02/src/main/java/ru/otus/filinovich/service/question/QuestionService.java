package ru.otus.filinovich.service.question;

import ru.otus.filinovich.domain.ListOfQuestion;
import ru.otus.filinovich.domain.ResultTesting;
import ru.otus.filinovich.service.Service;

import java.io.BufferedReader;
import java.io.IOException;

public interface QuestionService extends Service {

    ListOfQuestion getQuestions();

    ResultTesting askQuestions(BufferedReader reader) throws IOException;
}
