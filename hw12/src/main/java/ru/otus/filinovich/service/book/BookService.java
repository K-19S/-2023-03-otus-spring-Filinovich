package ru.otus.filinovich.service.book;

import ru.otus.filinovich.domain.Book;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks();

    Book getBookById(String id);

    void save(Book book);

    void deleteById(String id);
}
