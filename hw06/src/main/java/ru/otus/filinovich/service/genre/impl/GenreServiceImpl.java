package ru.otus.filinovich.service.genre.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.filinovich.dao.genre.GenreRepository;
import ru.otus.filinovich.domain.Genre;
import ru.otus.filinovich.service.genre.GenreService;
import ru.otus.filinovich.service.user.interaction.UserInteractionService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    private final UserInteractionService userInteractionService;

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.getAll();
    }

    @Override
    public Genre getGenreById(Long id) {
        return genreRepository.getById(id).orElseThrow();
    }

    @Override
    public Genre getGenreByIdWithPromptAll() {
        Genre chosenGenre;
        do {
            Long genreId = userInteractionService.chooseGenreIdFromList(getAllGenres());
            chosenGenre = getGenreById(genreId);
        } while (chosenGenre == null);
        return chosenGenre;
    }
}
