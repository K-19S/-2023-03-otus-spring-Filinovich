package ru.otus.filinovich.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.filinovich.AbstractCommonTest;
import ru.otus.filinovich.domain.ListOfQuestion;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class QuestionDaoCsvTest extends AbstractCommonTest {

    @Autowired
    private QuestionDaoCsv questionDaoCsv;

    @Test
    void getQuestionsTest() {
        ListOfQuestion expected = generateTestListOfQuestion();
        ListOfQuestion actual = questionDaoCsv.getQuestions();

        assertThat(expected).isEqualTo(actual);
    }

}