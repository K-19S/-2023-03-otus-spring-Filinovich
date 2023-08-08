package ru.otus.filinovich.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "books")
public class Book {

    @Id
    private String id;

    private String name;

    @DBRef
    private Genre genre;

    @DBRef
    private List<Author> authors = new ArrayList<>();


    public Book(String name, List<Author> authors, Genre genre) {
        this.name = name;
        this.authors = authors;
        this.genre = genre;
    }

    public String getAuthorsInline() {
        return String.join(", ", authors.stream().map(Author::getName).toList());
    }
}

