package ru.otus.filinovich.domain;

import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode
public class ListOfQuestion {

    private final List<Question> questions;

    public ListOfQuestion(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return questions.stream()
                .map(Question::toString)
                .collect(Collectors.joining("\n\n"));
    }

}
