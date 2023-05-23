package ru.otus.filinovich.dao.book;

import ru.otus.filinovich.domain.Book;

import java.util.List;

public interface BookDao {

    List<Book> getAll();

    Book getById(Long id);

    boolean existById(Long id);

    Long create(Book book);

    void update(Book book);

    void deleteById(Long id);
}
