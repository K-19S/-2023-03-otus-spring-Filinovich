package ru.otus.filinovich.dao;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import ru.otus.filinovich.CommonTest;
import ru.otus.filinovich.domain.ListOfQuestion;

import static org.assertj.core.api.Assertions.assertThat;

class QuestionDaoCsvTest extends CommonTest {

    private static final QuestionDaoCsv questionDaoCsv = new QuestionDaoCsv();

    @Test
    void getQuestionsTest() {
        ReflectionTestUtils.setField(questionDaoCsv, "csvFileName", "test.csv");
        ListOfQuestion expected = generateTestListOfQuestion();
        ListOfQuestion actual = questionDaoCsv.getQuestions();

        assertThat(expected).isEqualTo(actual);
    }

}