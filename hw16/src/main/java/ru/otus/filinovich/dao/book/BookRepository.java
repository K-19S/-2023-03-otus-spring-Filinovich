package ru.otus.filinovich.dao.book;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.filinovich.domain.Author;
import ru.otus.filinovich.domain.Book;
import ru.otus.filinovich.domain.Genre;

import java.util.List;

@RepositoryRestResource(path = "book")
public interface BookRepository extends MongoRepository<Book, String> {

    List<Book> findByGenre(Genre genre);

    List<Book> findByAuthorsContaining(Author author);
}
