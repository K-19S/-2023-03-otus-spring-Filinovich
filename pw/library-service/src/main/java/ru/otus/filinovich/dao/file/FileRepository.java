package ru.otus.filinovich.dao.file;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.filinovich.domain.Book;
import ru.otus.filinovich.domain.BookFile;

@Repository
public interface FileRepository extends MongoRepository<BookFile, String> {

    BookFile getByBook(Book book);
}
