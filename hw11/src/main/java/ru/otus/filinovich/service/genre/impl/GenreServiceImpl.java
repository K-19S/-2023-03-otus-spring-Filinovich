package ru.otus.filinovich.service.genre.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.filinovich.dao.genre.GenreRepository;
import ru.otus.filinovich.domain.Genre;
import ru.otus.filinovich.service.genre.GenreService;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public Flux<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    public Mono<Genre> getById(String id) {
        return genreRepository.findById(id);
    }

    @Override
    public Mono<Genre> save(Mono<Genre> genre) {
        return genre.flatMap(genreRepository::save);
    }

    @Override
    public Mono<Genre> update(Mono<Genre> genre) {
        return genre.filter(var -> var.getId() != null && !var.getId().isBlank())
                .flatMap(genreRepository::save);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return genreRepository.deleteById(id);
    }
}
