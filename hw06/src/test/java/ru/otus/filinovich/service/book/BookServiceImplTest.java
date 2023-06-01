package ru.otus.filinovich.service.book;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.filinovich.dao.book.BookRepository;
import ru.otus.filinovich.domain.Author;
import ru.otus.filinovich.domain.Book;
import ru.otus.filinovich.domain.Genre;
import ru.otus.filinovich.service.IOService;
import ru.otus.filinovich.service.author.AuthorService;
import ru.otus.filinovich.service.genre.GenreService;
import ru.otus.filinovich.util.MessageProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Component
@ExtendWith(SpringExtension.class)
@Import(BookServiceImpl.class)
class BookServiceImplTest {

    private static final Long CORRECT_BOOK_ID = 1L;

    private static final Long INCORRECT_BOOK_ID = -100L;

    private static List<Book> testBooks;

    @Autowired
    private BookServiceImpl bookService;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private IOService ioService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private MessageProvider messageProvider;

    @BeforeAll
    public static void initBooks() {
        testBooks = new ArrayList<>(2);
        testBooks.add(new Book("TestBook1", List.of(new Author(1L, "TestAuthor1")), new Genre(1L, "TestGenre1")));
        testBooks.add(new Book("TestBook2", List.of(new Author(2L, "TestAuthor2")), new Genre(2L, "TestGenre2")));
    }

    @Test
    public void getAllBooksTest() {
        List<Book> expected = testBooks;
        given(bookRepository.getAll()).willReturn(expected);
        List<Book> actual = bookService.getAllBooks();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getAllBooksDescriptionsTest() {
        List<Book> expectedList = testBooks;
        given(bookRepository.getAll()).willReturn(expectedList);
        StringBuilder builder = new StringBuilder();
        for (Book book : expectedList) {
            String authorName = book.getAuthors().get(0).getName();
            String genreName = book.getGenre().getName();
            builder.append("\"")
                    .append(book.getName()).append("\" ")
                    .append(authorName).append(", ")
                    .append(genreName).append(" | ID: ")
                    .append(book.getId()).append("\n");
        }
        String actual = bookService.getAllBooksDescriptions();
        assertThat(actual).isEqualTo(builder.toString());
        verify(bookRepository, times(1)).getAll();
    }

    @Test
    public void getBookByCorrectIdTest() {
        Book testBook = new Book("TestBook1", List.of(new Author(1L, "TestAuthor1")), new Genre(1L, "TestGenre1"));
        given(bookRepository.getById(CORRECT_BOOK_ID)).willReturn(Optional.of(testBook));
        Book actual = bookService.getBookById(CORRECT_BOOK_ID);

        assertThat(testBook).isEqualTo(actual);
        verify(bookRepository, times(1)).getById(CORRECT_BOOK_ID);
    }

    @Test
    public void getBookByIncorrectIdTest() {
        given(bookRepository.getById(INCORRECT_BOOK_ID)).willReturn(Optional.empty());
        assertThatExceptionOfType(Exception.class).isThrownBy(() -> bookService.getBookById(INCORRECT_BOOK_ID));
        verify(bookRepository, times(1)).getById(INCORRECT_BOOK_ID);
    }

    @Test
    public void getBookDescriptionByIdTest() {
        Book testBook = testBooks.get(0);
        given(bookRepository.getById(testBook.getId())).willReturn(Optional.of(testBook));
        String authorName = testBook.getAuthors().get(0).getName();
        String genreName = testBook.getGenre().getName();
        String expected = "\"" + testBook.getName() + "\" " + authorName + ", " + genreName;
        String actual = bookService.getBookDescriptionById(testBook.getId());

        assertThat(actual).isEqualTo(expected);
        verify(bookRepository, times(1)).getById(testBook.getId());
    }

    @Test
    public void getBookByIdWithPromptAllTest() {
        String prompt = "Test prompt";
        given(bookRepository.getAll()).willReturn(testBooks);
        given(ioService.readLongWithPrompt(prompt)).willReturn(1L);
        given(bookRepository.getById(1L)).willReturn(Optional.of(testBooks.get(0)));

        Book actual = bookService.getBookByIdWithPromptAll(prompt);
        Book expected = testBooks.get(0);

        assertThat(actual).isEqualTo(expected);
        verify(bookRepository, times(1)).getAll();
        verify(ioService, times(1)).readLongWithPrompt(prompt);
        verify(bookRepository, times(1)).getById(1L);
    }

    @Test
    public void createNewBookTest() {
        Book testBook = testBooks.get(0);
        given(ioService.readStringWithPrompt(any())).willReturn(testBook.getName());
        given(genreService.getGenreByIdWithPromptAll()).willReturn(testBook.getGenre());
        given(authorService.getAuthorByIdWithPromptAll()).willReturn(testBook.getAuthors().get(0));
        bookService.createNewBook();

        verify(ioService, times(1)).readStringWithPrompt(any());
        verify(genreService, times(1)).getGenreByIdWithPromptAll();
        verify(authorService, times(1)).getAuthorByIdWithPromptAll();
        verify(bookRepository, times(1)).create(testBook);
    }

    @Test
    public void getDescriptionOfBookTest() {
        Book testBook = testBooks.get(0);
        String authorName = testBook.getAuthors().get(0).getName();
        String genreName = testBook.getGenre().getName();
        String expected = "\"" + testBook.getName() + "\" " + authorName + ", " + genreName;
        String actual = bookService.getDescriptionOfBook(testBook);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getDescriptionOfBookWithIdTest() {
        Book testBook = testBooks.get(0);
        String authorName = testBook.getAuthors().get(0).getName();
        String genreName = testBook.getGenre().getName();
        String expected = "\"" + testBook.getName() + "\" " + authorName + ", " + genreName
                + " | ID: " + testBook.getId();
        String actual = bookService.getDescriptionOfBookWithId(testBook);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void updateBookNameTest() {
        Book expectedBook = testBooks.get(0);

        given(messageProvider.getMessage("book.name")).willReturn("Name");
        given(messageProvider.getMessage("book.author")).willReturn("Author");
        given(messageProvider.getMessage("book.genre")).willReturn("Genre");
        given(ioService.readLongWithPrompt(null)).willReturn(1L);
        String testName = "NewName";
        given(ioService.readStringWithPrompt(anyString())).willReturn(testName);
        bookService.updateBook(expectedBook);
        expectedBook.setName(testName);

        verify(bookRepository, times(1)).update(expectedBook);
    }

    @Test
    public void updateBookAuthorTest() {
        Book expectedBook = testBooks.get(0);

        given(messageProvider.getMessage("book.name")).willReturn("Name");
        given(messageProvider.getMessage("book.author")).willReturn("Author");
        given(messageProvider.getMessage("book.genre")).willReturn("Genre");
        given(ioService.readLongWithPrompt(null)).willReturn(2L);
        given(authorService.getAuthorByIdWithPromptAll()).willReturn(testBooks.get(1).getAuthors().get(0));
        bookService.updateBook(expectedBook);
        expectedBook.setAuthors(testBooks.get(1).getAuthors());

        verify(bookRepository, times(1)).update(expectedBook);
    }

    @Test
    public void updateBookGenreTest() {
        Book expectedBook = testBooks.get(0);

        given(messageProvider.getMessage("book.name")).willReturn("Name");
        given(messageProvider.getMessage("book.author")).willReturn("Author");
        given(messageProvider.getMessage("book.genre")).willReturn("Genre");
        given(ioService.readLongWithPrompt(null)).willReturn(3L);
        given(genreService.getGenreByIdWithPromptAll()).willReturn(testBooks.get(1).getGenre());
        bookService.updateBook(expectedBook);
        expectedBook.setGenre(testBooks.get(1).getGenre());

        verify(bookRepository, times(1)).update(expectedBook);
    }

    @Test
    public void deleteByIdTest() {
        given(bookRepository.existById(CORRECT_BOOK_ID)).willReturn(true);
        assertThat(bookService.deleteById(-INCORRECT_BOOK_ID)).isFalse();
        verify(bookRepository, times(1)).existById(-INCORRECT_BOOK_ID);
        assertThat(bookService.deleteById(CORRECT_BOOK_ID)).isTrue();
        verify(bookRepository, times(1)).existById(CORRECT_BOOK_ID);
        verify(bookRepository, times(1)).deleteById(any());
        verify(bookRepository, times(1)).deleteById(CORRECT_BOOK_ID);
    }
}