package ru.otus.filinovich.service.genre.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.filinovich.dao.genre.GenreRepository;
import ru.otus.filinovich.domain.Genre;
import ru.otus.filinovich.service.genre.GenreService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    public Genre getById(String id) {
        return genreRepository.findById(id).orElseThrow();
    }

    @Override
    public void save(Genre genre) {
        genreRepository.save(genre);
    }

    @Override
    public boolean update(Genre genre) {
        if (genre.getId() == null || genre.getId().isBlank()) {
            return false;
        }
        genreRepository.save(genre);
        return true;
    }


    @Override
    public void deleteById(String id) {
        genreRepository.deleteById(id);
    }
}
