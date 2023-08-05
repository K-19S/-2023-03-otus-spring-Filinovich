package ru.otus.filinovich.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.filinovich.domain.Author;
import ru.otus.filinovich.domain.Book;
import ru.otus.filinovich.domain.BookComment;
import ru.otus.filinovich.domain.Genre;
import ru.otus.filinovich.dto.BookDto;
import ru.otus.filinovich.security.SecurityConfiguration;
import ru.otus.filinovich.service.author.AuthorService;
import ru.otus.filinovich.service.book.BookService;
import ru.otus.filinovich.service.book_comment.BookCommentService;
import ru.otus.filinovich.service.genre.GenreService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;


// По другому никак не сработало, в том числе и
// @WebMvcTest(BookController.class)
@WebMvcTest
@ContextConfiguration(classes = {
        BookController.class,
        SecurityConfiguration.class})
class BookControllerTest {

    private static final List<Book> TEST_BOOKS = new ArrayList<>();

    private static final List<BookComment> TEST_BOOK_COMMENTS = new ArrayList<>();

    private static final List<Genre> TEST_GENRES = new ArrayList<>();

    private static final List<Author> TEST_AUTHORS = new ArrayList<>();

    private static final String TEST_ID = "TEST_BOOK_ID";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private BookCommentService bookCommentService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private UserDetailsService userDetailsService;

    @BeforeAll
    public static void initBooks() {
        TEST_BOOKS.add(new Book(TEST_ID, "Book1", new Genre("Genre1"), List.of(new Author("Author1"))));
        TEST_BOOKS.add(new Book("Book2", List.of(new Author("Author2")), new Genre("Genre2")));

        TEST_BOOK_COMMENTS.add(new BookComment("BookComment1", TEST_BOOKS.get(0)));

        TEST_GENRES.add(new Genre("Genre1"));

        TEST_AUTHORS.add(new Author("Author1"));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    public void allBooksTest() throws Exception {
        given(bookService.getAllBooks()).willReturn(TEST_BOOKS);

        mvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(view().name("books"))
                .andExpect(model().attribute("books", TEST_BOOKS));

        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    public void allBooksSecurityTest() throws Exception {
        mvc.perform(get("/books"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("location", "http://localhost/login"));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    public void editBookTest() throws Exception {
        given(bookService.getBookById(TEST_ID)).willReturn(TEST_BOOKS.get(0));
        given(bookCommentService.getAllCommentsByBookId(TEST_ID)).willReturn(TEST_BOOK_COMMENTS);
        given(genreService.getAllGenres()).willReturn(TEST_GENRES);
        given(authorService.getAllAuthors()).willReturn(TEST_AUTHORS);

        mvc.perform(get("/book").param("id", TEST_ID))
                .andExpect(status().isOk())
                .andExpect(view().name("editBook"))
                .andExpect(model().attribute("book", BookDto.toDto(TEST_BOOKS.get(0))))
                .andExpect(model().attribute("comments", TEST_BOOK_COMMENTS))
                .andExpect(model().attribute("genres", TEST_GENRES))
                .andExpect(model().attribute("authors", TEST_AUTHORS))
                .andExpect(model().attribute("comment", new BookComment()));

        verify(bookService, times(1)).getBookById(TEST_ID);
        verify(bookCommentService, times(1)).getAllCommentsByBookId(TEST_ID);
        verify(genreService, times(1)).getAllGenres();
        verify(authorService, times(1)).getAllAuthors();
    }

    @Test
    public void editBookSecurityTest() throws Exception {
        mvc.perform(get("/book").param("id", TEST_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("location", "http://localhost/login"));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    public void newBookTest() throws Exception {
        given(genreService.getAllGenres()).willReturn(TEST_GENRES);
        given(authorService.getAllAuthors()).willReturn(TEST_AUTHORS);

        mvc.perform(get("/newBook"))
                .andExpect(status().isOk())
                .andExpect(view().name("editBook"))
                .andExpect(model().attribute("book", new BookDto()))
                .andExpect(model().attribute("comments", new ArrayList<>()))
                .andExpect(model().attribute("genres", TEST_GENRES))
                .andExpect(model().attribute("authors", TEST_AUTHORS))
                .andExpect(model().attribute("comment", new BookComment()));

        verify(genreService, times(1)).getAllGenres();
        verify(authorService, times(1)).getAllAuthors();
    }

    @Test
    public void newBookSecurityTest() throws Exception {
        mvc.perform(get("/newBook"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("location", "http://localhost/login"));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    public void deleteBookTest() throws Exception {
        given(bookService.getAllBooks()).willReturn(TEST_BOOKS);

        mvc.perform(get("/deleteBook").param("id", TEST_ID))
                .andExpect(status().isOk())
                .andExpect(view().name("books"))
                .andExpect(model().attribute("books", TEST_BOOKS));

        verify(bookService, times(1)).deleteById(TEST_ID);
        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    public void deleteBookSecurityTest() throws Exception {
        mvc.perform(get("/deleteBook").param("id", TEST_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("location", "http://localhost/login"));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    public void saveBookTest() throws Exception {
        given(bookService.getAllBooks()).willReturn(TEST_BOOKS);

        mvc.perform(post("/book")
                    .flashAttr("book", BookDto.toDto(TEST_BOOKS.get(0)))
                    .with(csrf()))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:/books"));

        verify(bookService, times(1)).save(TEST_BOOKS.get(0));
    }

    @Test
    public void saveBookSecurityTest() throws Exception {
        mvc.perform(post("/book"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("location", "http://localhost/login"));
    }
}