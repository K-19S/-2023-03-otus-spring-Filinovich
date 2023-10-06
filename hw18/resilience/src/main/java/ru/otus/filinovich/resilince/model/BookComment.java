package ru.otus.filinovich.resilince.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookComment {

    private String id;

    private String text;

    private Book book;

    public BookComment(String text, Book book) {
        this.text = text;
        this.book = book;
    }
}
