package ru.otus.filinovich.dao.genre;

import ru.otus.filinovich.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {

    List<Genre> getAll();

    Optional<Genre> getById(Long id);
}
