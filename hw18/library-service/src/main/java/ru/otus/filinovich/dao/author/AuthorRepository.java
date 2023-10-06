package ru.otus.filinovich.dao.author;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.filinovich.domain.Author;

@RepositoryRestResource(path = "author")
public interface AuthorRepository extends MongoRepository<Author, String> {

}
