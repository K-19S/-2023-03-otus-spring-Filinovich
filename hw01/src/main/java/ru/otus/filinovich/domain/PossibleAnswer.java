package ru.otus.filinovich.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class PossibleAnswer {

    private final String text;

    public PossibleAnswer(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
