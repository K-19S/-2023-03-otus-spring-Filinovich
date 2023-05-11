package ru.otus.filinovich.domain;

import lombok.Data;

@Data
public class User {

    private String name;

    private String surname;

    @Override
    public String toString() {
        return name + " " + surname;
    }
}
