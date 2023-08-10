package ru.otus.filinovich.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book_comments")
public class BookComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ManyToOne
    private Book book;

    public BookComment(String text, Book book) {
        this.text = text;
        this.book = book;
    }
}
