package ru.otus.filinovich.dao.author;

import ru.otus.filinovich.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    List<Author> getAll();

    Optional<Author> getById(Long id);
}
