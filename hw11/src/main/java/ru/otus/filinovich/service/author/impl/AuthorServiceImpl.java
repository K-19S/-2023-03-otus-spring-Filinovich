package ru.otus.filinovich.service.author.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.filinovich.dao.author.AuthorRepository;
import ru.otus.filinovich.domain.Author;
import ru.otus.filinovich.service.author.AuthorService;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public Flux<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Mono<Author> getById(String id) {
        return authorRepository.findById(id);
    }

    @Override
    public Mono<Author> save(Mono<Author> author) {
        return author.flatMap(authorRepository::save);
    }

    @Override
    public Mono<Author> update(Mono<Author> author) {
        return author.filter(var -> var.getId() != null && !var.getId().isBlank())
                .flatMap(authorRepository::save);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return authorRepository.deleteById(id);
    }
}
