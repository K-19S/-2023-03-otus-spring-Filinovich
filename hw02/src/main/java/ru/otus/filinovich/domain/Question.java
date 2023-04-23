package ru.otus.filinovich.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@EqualsAndHashCode
public class Question {

    private final String text;

    private final String correctAnswer;

    private final List<PossibleAnswer> possibleAnswers;

    public Question(String text, String correctAnswer, String[] answers) {
        this.text = text;
        this.correctAnswer = correctAnswer;
        this.possibleAnswers = new ArrayList<>();
        for (String answer : answers) {
            possibleAnswers.add(new PossibleAnswer(answer));
        }
    }

    @Override
    public String toString() {
        return text + '\n' +
                possibleAnswers.stream()
                        .map(PossibleAnswer::getText)
                        .collect(Collectors.joining("\n"));
    }
}
