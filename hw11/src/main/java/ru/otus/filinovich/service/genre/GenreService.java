package ru.otus.filinovich.service.genre;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.filinovich.domain.Genre;

public interface GenreService {

    Flux<Genre> getAllGenres();

    Mono<Genre> getById(String id);

    Mono<Genre> save(Mono<Genre> genre);

    Mono<Genre> update(Mono<Genre> genre);

    Mono<Void> deleteById(String id);
}
