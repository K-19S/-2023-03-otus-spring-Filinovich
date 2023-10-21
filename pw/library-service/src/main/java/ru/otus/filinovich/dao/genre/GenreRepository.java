package ru.otus.filinovich.dao.genre;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.filinovich.domain.Genre;

@RepositoryRestResource(path = "genre")
public interface GenreRepository extends MongoRepository<Genre, String> {

}
