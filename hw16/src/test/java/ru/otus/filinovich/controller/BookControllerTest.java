package ru.otus.filinovich.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.filinovich.domain.Author;
import ru.otus.filinovich.domain.Book;
import ru.otus.filinovich.domain.Genre;
import ru.otus.filinovich.dto.BookDto;
import ru.otus.filinovich.service.book.BookService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest
@ContextConfiguration(classes = BookController.class)
class BookControllerTest {

    private static final List<Book> TEST_BOOKS = new ArrayList<>();

    private static final Author TEST_AUTHOR = new Author();

    private static final Genre TEST_GENRE = new Genre();

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    @BeforeAll
    public static void initTestBooks() {
        TEST_AUTHOR.setId("64c637f8055ba60bd79dbf79");
        TEST_AUTHOR.setName("Author1");

        TEST_GENRE.setId("64c637f8055ba60bd79dbf72");
        TEST_GENRE.setName("Genre1");
        TEST_BOOKS.add(new Book("64c637f8055ba60bd79dbf83", "Book1", TEST_GENRE, List.of(TEST_AUTHOR)));
        TEST_BOOKS.add(new Book("64c637f8055ba60bd79dbf84", "Book2", TEST_GENRE, List.of(TEST_AUTHOR)));
    }

    @Test
    void getAllBooksTest() throws Exception {
        given(bookService.getAllBooks()).willReturn(TEST_BOOKS);

        String json = objectMapper.writeValueAsString(TEST_BOOKS.stream().map(BookDto::toDto).toList());
        mvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().json(json));

        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void createNewBookTest() throws Exception {
        Book testBook = TEST_BOOKS.get(0);
        String json = objectMapper.writeValueAsString(BookDto.toDto(testBook));
        mvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        verify(bookService, times(1)).save(testBook);
    }

    @Test
    void updateBookTest() throws Exception {
        Book testBook = TEST_BOOKS.get(0);
        given(bookService.update(testBook)).willReturn(true);
        String json = objectMapper.writeValueAsString(BookDto.toDto(testBook));
        mvc.perform(put("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(bookService, times(1)).update(testBook);
    }

    @Test
    void updateBookFailedTest() throws Exception {
        Book testBook = new Book(
                TEST_BOOKS.get(0).getName(),
                TEST_BOOKS.get(0).getAuthors(),
                TEST_BOOKS.get(0).getGenre());
        given(bookService.update(testBook)).willReturn(false);
        String json = objectMapper.writeValueAsString(BookDto.toDto(testBook));
        mvc.perform(put("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());

        verify(bookService, times(1)).update(testBook);
    }
}