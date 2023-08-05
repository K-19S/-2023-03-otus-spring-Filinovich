package ru.otus.filinovich.dao.author;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.filinovich.domain.Author;

public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {

}
