package ru.otus.filinovich.service.files;

import ru.otus.filinovich.domain.Book;
import ru.otus.filinovich.domain.BookFile;

public interface FileService {

    void save(BookFile file);

    BookFile getByBook(Book book);
}
