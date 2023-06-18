package ru.otus.filinovich.service.user.interaction.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.filinovich.domain.Author;
import ru.otus.filinovich.domain.Book;
import ru.otus.filinovich.domain.Genre;
import ru.otus.filinovich.service.io.IOService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Component
@ExtendWith(SpringExtension.class)
@Import(UserInteractionServiceImpl.class)
class UserInteractionServiceImplTest {

    private static List<Genre> testGenres;

    private static List<Author> testAuthors;

    private static List<Book> testBooks;

    @Autowired
    private UserInteractionServiceImpl userInteractionService;

    @MockBean
    private IOService ioService;

    @BeforeAll
    public static void initBooks() {
        testAuthors = new ArrayList<>(2);
        testAuthors.add(new Author(1L, "TestAuthor1"));
        testAuthors.add(new Author(2L, "TestAuthor2"));
        testGenres = new ArrayList<>(2);
        testGenres.add(new Genre(1L, "TestGenre1"));
        testGenres.add(new Genre(2L, "TestGenre2"));
        testBooks = new ArrayList<>(2);
        testBooks.add(new Book("TestBook1", List.of(testAuthors.get(0)), testGenres.get(0)));
        testBooks.add(new Book("TestBook2", List.of(testAuthors.get(1)), testGenres.get(1)));
    }

    @Test
    public void showTextTest() {
        String text = "TestText";
        userInteractionService.showText(text);
        verify(ioService, times(1)).outputString(text);
    }

    @Test
    public void getTextTest() {
        userInteractionService.getText();
        verify(ioService, times(1)).readString();
    }

    @Test
    public void getLongTest() {
        userInteractionService.getLong();
        verify(ioService, times(1)).readLong();
    }

    @Test
    public void getTextWithPromptTest() {
        String testPrompt = "TestPrompt";
        userInteractionService.getTextWithPrompt(testPrompt);
        verify(ioService, times(1)).readStringWithPrompt(testPrompt);
    }

    @Test
    public void getLongWithPromptTest() {
        String testPrompt = "TestPrompt";
        userInteractionService.getLongWithPrompt(testPrompt);
        verify(ioService, times(1)).readLongWithPrompt(testPrompt);
    }

    @Test
    public void chooseBookIdFromListTest() {
        Long actual = 1L;
        given(ioService.readLong(0, testBooks.size())).willReturn(actual);
        Long expected = userInteractionService.chooseBookIdFromList(testBooks);
        verify(ioService, times(1)).readLong(0, testBooks.size());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void chooseGenreIdFromListTest() {
        Long actual = 1L;
        given(ioService.readLong(0, testGenres.size())).willReturn(actual);
        Long expected = userInteractionService.chooseGenreIdFromList(testGenres);
        verify(ioService, times(testGenres.size())).outputString(anyString());
        verify(ioService, times(1)).readLong(0, testGenres.size());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void chooseAuthorIdFromListTest() {
        Long actual = 1L;
        given(ioService.readLong(0, testAuthors.size())).willReturn(actual);
        Long expected = userInteractionService.chooseAuthorIdFromList(testAuthors);
        verify(ioService, times(testAuthors.size())).outputString(anyString());
        verify(ioService, times(1)).readLong(0, testAuthors.size());
        assertThat(actual).isEqualTo(expected);
    }

}