package ru.otus.filinovich.resilince.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Author {

    private String id;

    private String name;

    public Author(String name) {
        this.name = name;
    }
}


