package ru.otus.filinovich.service.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.filinovich.dao.genre.GenreRepository;
import ru.otus.filinovich.domain.Genre;
import ru.otus.filinovich.service.IOService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    private final IOService ioService;

    @Override
    @Transactional(readOnly = true)
    public List<Genre> getAllGenres() {
        return genreRepository.getAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Genre getGenreById(Long id) {
        return genreRepository.getById(id).orElseThrow();
    }

    @Override
    public Genre getGenreByIdWithPromptAll() {
        getAllGenres().forEach(genre -> {
            ioService.outputString(genre.getId() + ". " + genre.getName());
        });
        Genre chosenGenre = null;
        do {
            Long genreId = ioService.readLong();
            chosenGenre = getGenreById(genreId);
        } while (chosenGenre == null);
        return chosenGenre;
    }
}
