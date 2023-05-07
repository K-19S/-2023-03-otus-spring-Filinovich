package ru.otus.filinovich.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class PossibleAnswer {

    private final String text;

    public PossibleAnswer(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
