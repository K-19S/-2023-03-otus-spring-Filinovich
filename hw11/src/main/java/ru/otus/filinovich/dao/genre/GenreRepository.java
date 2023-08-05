package ru.otus.filinovich.dao.genre;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.filinovich.domain.Genre;

public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {

}
