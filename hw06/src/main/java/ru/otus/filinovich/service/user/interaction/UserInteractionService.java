package ru.otus.filinovich.service.user.interaction;

import ru.otus.filinovich.domain.Author;
import ru.otus.filinovich.domain.Book;
import ru.otus.filinovich.domain.Genre;

import java.util.List;

public interface UserInteractionService {

    void showText(String text);

    String getText();

    Long getLong();

    String getTextWithPrompt(String promt);

    Long getLongWithPrompt(String promt);

    Long chooseBookIdFromList(List<Book> allBooks);

    Long chooseAuthorIdFromList(List<Author> allAuthors);

    Long chooseGenreIdFromList(List<Genre> allGenres);
}
