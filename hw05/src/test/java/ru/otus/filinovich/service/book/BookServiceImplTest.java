package ru.otus.filinovich.service.book;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.filinovich.dao.book.BookDao;
import ru.otus.filinovich.domain.Author;
import ru.otus.filinovich.domain.Book;
import ru.otus.filinovich.domain.Genre;
import ru.otus.filinovich.service.IOService;
import ru.otus.filinovich.service.author.AuthorService;
import ru.otus.filinovich.service.genre.GenreService;
import ru.otus.filinovich.util.MessageProvider;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Component
@Import(BookServiceImpl.class)
@ExtendWith(SpringExtension.class)
class BookServiceImplTest {

    private static List<Book> expectedBooks;

    @Autowired
    private BookServiceImpl bookService;

    @MockBean
    private BookDao bookDao;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private IOService ioService;

    @MockBean
    private MessageProvider messageProvider;

    @BeforeAll
    public static void initBooks() {
        expectedBooks = new ArrayList<>();
        expectedBooks.add(new Book(1L, "Ten Little Niggers",
                new Author(1L, "Agatha Christie"), new Genre(1L, "Mystery")));
        expectedBooks.add(new Book(2L, "The Murder House",
                new Author(2L, "James Patterson"), new Genre(2L, "Thriller")));
        expectedBooks.add(new Book(3L, "IT",
                new Author(3L, "Stephen King"), new Genre(3L, "Horror")));
        expectedBooks.add(new Book(4L, "At the Mountains of Madness",
                new Author(4L, "Howard Lovecraft"), new Genre(3L, "Horror")));
        expectedBooks.add(new Book(5L, "Lord John and the Private Matter",
                new Author(5L, "Diana Gabaldon"), new Genre(4L, "Historical")));
        expectedBooks.add(new Book(6L, "Sackett",
                new Author(6L, "Louis Lamour"), new Genre(5L, "Western")));
        expectedBooks.add(new Book(7L, "A Game of Thrones",
                new Author(7L, "George R. R. Martin"), new Genre(6L, "Fantasy")));
        expectedBooks.add(new Book(8L, "Harry Potter and the Deathly Hallows",
                new Author(8L, "Joanne Rowling"), new Genre(6L, "Fantasy")));
    }

    @Test
    public void getAllBooksTest() {
        given(bookDao.getAll()).willReturn(expectedBooks);
        List<Book> actual = bookService.getAllBooks();
        assertThat(actual).isEqualTo(expectedBooks);
    }

    @Test
    public void getBookByIdCorrectTest() {
        Book expected = expectedBooks.get(1);
        given(bookDao.getById(1L)).willReturn(expected);
        Book actual = bookService.getBookById(1L);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getBookByIdUncorrectTest() {
        given(bookDao.getById(100L)).willReturn(null);
        Book actual = bookService.getBookById(100L);
        assertThat(actual).isNull();
    }

    @Test
    public void getBookIdWithPromptAllTest() {

        String testPrompt = "TestPrompt";
        Book expected = expectedBooks.get(3);
        given(ioService.readLongWithPrompt(testPrompt)).willReturn(-100L, 0L, 100L, 3L);
        given(bookDao.getById(3L)).willReturn(expected);
        given(bookDao.getAll()).willReturn(expectedBooks);

        Book actual = bookService.getBookByIdWithPromptAll(testPrompt);

        assertThat(actual).isEqualTo(expected);

        expectedBooks.forEach(book -> {
            verify((ioService), times(1)).outputString(bookService.getDescriptionOfBookWithId(book));
        });
    }

    @Test
    public void createNewBookTest() {
        Book expectedBook = new Book("TestBookName",
                expectedBooks.get(1).getAuthor(), expectedBooks.get(1).getGenre());

        given(ioService.readStringWithPrompt(any())).willReturn("TestBookName");
        given(genreService.getGenreByIdWithPromptAll()).willReturn(expectedBooks.get(1).getGenre());
        given(authorService.getAuthorByIdWithPromptAll()).willReturn(expectedBooks.get(1).getAuthor());
        given(bookDao.create(any())).willReturn(9L);
        given(bookDao.getById(9L)).willReturn(expectedBook);
        expectedBook.setId(9L);

        Book actual = bookService.createNewBook();

        assertThat(actual).isEqualTo(expectedBook);
    }

    @Test
    public void getDescriptionOfBookTest() {
        Book expectedBook = new Book("TestBookName",
                expectedBooks.get(1).getAuthor(), expectedBooks.get(1).getGenre());

        String expected = "\"TestBookName\" James Patterson, Thriller";
        String actual = bookService.getDescriptionOfBook(expectedBook);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getDescriptionOfBookWithIdTest() {
        Book expectedBook = new Book(9L, "TestBookName",
                expectedBooks.get(1).getAuthor(), expectedBooks.get(1).getGenre());

        String expected = "\"TestBookName\" James Patterson, Thriller | ID: 9";
        String actual = bookService.getDescriptionOfBookWithId(expectedBook);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void updateBookNameTest() {
        Book expectedBook = new Book(8L, "Harry Potter and the Deathly Hallows",
                new Author(8L, "Joanne Rowling"), new Genre(6L, "Fantasy"));

        given(messageProvider.getMessage("book.name")).willReturn("Name");
        given(messageProvider.getMessage("book.author")).willReturn("Author");
        given(messageProvider.getMessage("book.genre")).willReturn("Genre");
        given(ioService.readLongWithPrompt(null)).willReturn(1L);
        String testName = "NewName";
        given(ioService.readStringWithPrompt(anyString())).willReturn(testName);
        bookService.updateBook(expectedBook);
        expectedBook.setName(testName);

        verify(bookDao, times(1)).update(expectedBook);
    }

    @Test
    public void updateBookAuthorTest() {
        Book expectedBook = new Book(8L, "Harry Potter and the Deathly Hallows",
                new Author(8L, "Joanne Rowling"), new Genre(6L, "Fantasy"));

        given(messageProvider.getMessage("book.name")).willReturn("Name");
        given(messageProvider.getMessage("book.author")).willReturn("Author");
        given(messageProvider.getMessage("book.genre")).willReturn("Genre");
        given(ioService.readLongWithPrompt(null)).willReturn(2L);
        given(authorService.getAuthorByIdWithPromptAll()).willReturn(expectedBooks.get(5).getAuthor());
        bookService.updateBook(expectedBook);
        expectedBook.setAuthor(expectedBooks.get(5).getAuthor());

        verify(bookDao, times(1)).update(expectedBook);
    }

    @Test
    public void updateBookGenreTest() {
        Book expectedBook = new Book(8L, "Harry Potter and the Deathly Hallows",
                new Author(8L, "Joanne Rowling"), new Genre(6L, "Fantasy"));

        given(messageProvider.getMessage("book.name")).willReturn("Name");
        given(messageProvider.getMessage("book.author")).willReturn("Author");
        given(messageProvider.getMessage("book.genre")).willReturn("Genre");
        given(ioService.readLongWithPrompt(null)).willReturn(3L);
        given(genreService.getGenreByIdWithPromptAll()).willReturn(expectedBooks.get(5).getGenre());
        bookService.updateBook(expectedBook);
        expectedBook.setGenre(expectedBooks.get(5).getGenre());

        verify(bookDao, times(1)).update(expectedBook);
    }

    @Test
    public void deleteByIdTest() {
        given(bookDao.existById(5L)).willReturn(true);
        assertThat(bookService.deleteById(-100L)).isFalse();
        verify(bookDao, times(1)).existById(-100L);
        assertThat(bookService.deleteById(0L)).isFalse();
        verify(bookDao, times(1)).existById(0L);
        assertThat(bookService.deleteById(100L)).isFalse();
        verify(bookDao, times(1)).existById(100L);
        assertThat(bookService.deleteById(5L)).isTrue();
        verify(bookDao, times(1)).existById(5L);
        verify(bookDao, times(1)).deleteById(any());
        verify(bookDao, times(1)).deleteById(5L);
    }

}