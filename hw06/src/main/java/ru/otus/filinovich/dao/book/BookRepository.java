package ru.otus.filinovich.dao.book;

import ru.otus.filinovich.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    List<Book> getAll();

    Optional<Book> getById(Long id);

    boolean existById(Long id);

    Book create(Book book);

    Book update(Book book);

    void deleteById(Long id);
}
