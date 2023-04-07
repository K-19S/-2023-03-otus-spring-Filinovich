package ru.otus.filinovich.domain;

import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode
public class Question {

    private final String text;

    private final List<PossibleAnswer> possibleAnswers;

    public Question(String text, String[] answers) {
        this.text = text;
        this.possibleAnswers = new ArrayList<>();
        for (String answer : answers) {
            possibleAnswers.add(new PossibleAnswer(answer));
        }
    }

    @Override
    public String toString() {
        return text + '\n' +
                possibleAnswers.stream()
                        .map(PossibleAnswer::toString)
                        .collect(Collectors.joining("\n"));
    }
}
