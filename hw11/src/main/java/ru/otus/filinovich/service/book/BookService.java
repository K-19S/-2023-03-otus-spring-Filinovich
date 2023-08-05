package ru.otus.filinovich.service.book;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.filinovich.domain.Author;
import ru.otus.filinovich.domain.Book;
import ru.otus.filinovich.domain.Genre;

public interface BookService {

    Flux<Book> getAllBooks();

    Flux<Book> getByGenre(Genre genre);

    Flux<Book> getByAuthor(Author author);

    Mono<Book> getBookById(String id);

    Mono<Book> save(Mono<Book> book);

    Mono<Book> update(Mono<Book> book);

    Mono<Void> deleteById(String id);

    void deleteAll(Flux<Book> books);
}
