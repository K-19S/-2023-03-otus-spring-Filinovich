package ru.otus.filinovich.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@EqualsAndHashCode
public class ResultTesting {

    private final Map<Question, String> answersList = new HashMap<>();
}
