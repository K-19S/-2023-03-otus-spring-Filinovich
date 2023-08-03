package ru.otus.filinovich.service.author;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.filinovich.domain.Author;

public interface AuthorService {

    Flux<Author> getAllAuthors();

    Mono<Author> getById(String id);

    Mono<Author> save(Mono<Author> author);

    Mono<Author> update(Mono<Author> author);

    Mono<Void> deleteById(String id);
}
