package ru.otus.filinovich.service.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.filinovich.dao.genre.GenreDao;
import ru.otus.filinovich.domain.Genre;
import ru.otus.filinovich.service.IOService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    private final IOService ioService;

    @Override
    public List<Genre> getAllGenres() {
        return genreDao.getAll();
    }

    @Override
    public Genre getGenreById(Long id) {
        return genreDao.getById(id);
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
