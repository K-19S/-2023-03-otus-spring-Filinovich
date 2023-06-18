package ru.otus.filinovich.service.user.interaction.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.filinovich.domain.Author;
import ru.otus.filinovich.domain.Book;
import ru.otus.filinovich.domain.Genre;
import ru.otus.filinovich.service.io.IOService;
import ru.otus.filinovich.service.user.interaction.UserInteractionService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserInteractionServiceImpl implements UserInteractionService {

    private final IOService ioService;

    @Override
    public void showText(String text) {
        ioService.outputString(text);
    }

    @Override
    public String getText() {
        return ioService.readString();
    }

    @Override
    public Long getLong() {
        return ioService.readLong();
    }

    @Override
    public String getTextWithPrompt(String promt) {
        return ioService.readStringWithPrompt(promt);
    }

    @Override
    public Long getLongWithPrompt(String promt) {
        return ioService.readLongWithPrompt(promt);
    }

    @Override
    public Long chooseBookIdFromList(List<Book> allBooks) {
        return ioService.readLong(0, allBooks.size());
    }

    @Override
    public Long chooseGenreIdFromList(List<Genre> allGenres) {
        allGenres.forEach(genre -> showText(genre.getId() + ". " + genre.getName()));
        return ioService.readLong(0, allGenres.size());
    }

    @Override
    public Long chooseAuthorIdFromList(List<Author> allAuthors) {
        allAuthors.forEach(author -> showText(author.getId() + ". " + author.getName()));
        return ioService.readLong(0, allAuthors.size());
    }
}

