package ru.otus.filinovich.dao.genre;

import ru.otus.filinovich.domain.Genre;

import java.util.List;

public interface GenreDao {

    List<Genre> getAll();

    Genre getById(Long id);
}
