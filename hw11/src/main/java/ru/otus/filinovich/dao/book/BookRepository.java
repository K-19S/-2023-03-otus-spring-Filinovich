package ru.otus.filinovich.dao.book;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.otus.filinovich.domain.Author;
import ru.otus.filinovich.domain.Book;
import ru.otus.filinovich.domain.Genre;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {

    Flux<Book> findByGenre(Genre genre);

    Flux<Book> findByAuthorsContaining(Author author);
}
