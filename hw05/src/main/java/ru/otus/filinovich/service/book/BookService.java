package ru.otus.filinovich.service.book;

import ru.otus.filinovich.domain.Book;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks();

    Book getBookById(Long id);

    Book getBookByIdWithPromptAll(String prompt);

    Book createNewBook();

    String getDescriptionOfBook(Book book);

    String getDescriptionOfBookWithId(Book book);

    void updateBook(Book book);

    boolean deleteById(Long id);
}
