package ru.otus.filinovich.service.genre;

import ru.otus.filinovich.domain.Genre;

import java.util.List;

public interface GenreService {

    List<Genre> getAllGenres();

    Genre getById(String id);

    void save(Genre genre);

    boolean update(Genre genre);

    void deleteById(String id);
}
