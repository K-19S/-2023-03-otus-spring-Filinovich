package ru.otus.filinovich.service.book;

import ru.otus.filinovich.domain.Author;
import ru.otus.filinovich.domain.Book;
import ru.otus.filinovich.domain.Genre;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks();

    List<Book> getByGenre(Genre genre);

    List<Book> getByAuthor(Author author);

    Book getBookById(String id);

    void save(Book book);

    boolean update(Book book);

    void deleteById(String id);

    void deleteAll(List<Book> books);
}
