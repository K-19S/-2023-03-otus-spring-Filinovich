package ru.otus.filinovich.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.filinovich.domain.ListOfQuestion;
import ru.otus.filinovich.domain.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class QuestionDaoCsv implements QuestionDao {

    private static final String COMMA_SEPARATOR = ",";

    @Value("${questions.csv.filename}")
    private String csvFileName;


    @Override
    public ListOfQuestion getQuestions() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(csvFileName)) {
            List<Question> questionList = new ArrayList<>();
            if (inputStream != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    Question question = createQuestionFromCsvLine(line);
                    questionList.add(question);
                }
            }
            return new ListOfQuestion(questionList);
        } catch (IOException e) {
            return new ListOfQuestion(new ArrayList<>());
        }
    }

    private Question createQuestionFromCsvLine(String csvLine) {
        String[] elements = csvLine.split(COMMA_SEPARATOR);
        int beginIndex = 2;
        int endIndex = elements.length;
        String[] answers = Arrays.copyOfRange(elements, beginIndex, endIndex);
        for (int i = 0; i < answers.length; i++) {
            answers[i] = (i + 1) + ". " + answers[i];
        }
        return new Question(elements[0], elements[1], answers);
    }
}
