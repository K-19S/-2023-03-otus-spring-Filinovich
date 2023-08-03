package ru.otus.filinovich.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.filinovich.domain.Author;
import ru.otus.filinovich.domain.Book;
import ru.otus.filinovich.domain.Genre;
import ru.otus.filinovich.dto.BookDto;
import ru.otus.filinovich.service.book.BookService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class BookConfigTest {

    private static final List<Book> TEST_BOOKS = new ArrayList<>();

    private static final Author TEST_AUTHOR = new Author();

    private static final Genre TEST_GENRE = new Genre();

    @Autowired
    private RouterFunction<ServerResponse> booksRouters;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    private WebTestClient client;

    @BeforeAll
    public static void initTestBooks() {
        TEST_AUTHOR.setId("64c637f8055ba60bd79dbf79");
        TEST_AUTHOR.setName("Author1");

        TEST_GENRE.setId("64c637f8055ba60bd79dbf72");
        TEST_GENRE.setName("Genre1");
        TEST_BOOKS.add(new Book("64c637f8055ba60bd79dbf83", "Book1", TEST_GENRE, List.of(TEST_AUTHOR)));
        TEST_BOOKS.add(new Book("64c637f8055ba60bd79dbf84", "Book2", TEST_GENRE, List.of(TEST_AUTHOR)));
    }

    @BeforeEach
    public void initWebTestClient() {
        client = WebTestClient
                .bindToRouterFunction(booksRouters)
                .build();
    }

    @Test
    void getAllBooksTest() throws Exception {
        given(bookService.getAllBooks()).willReturn(Flux.fromIterable(TEST_BOOKS));

        String json = objectMapper.writeValueAsString(TEST_BOOKS.stream().map(BookDto::toDto).toList());
        client.get()
                .uri("/books")
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(json);

        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void createNewBookTest() throws Exception {
        Book testBook = TEST_BOOKS.get(0);
        given(bookService.save(any())).willReturn(Mono.just(testBook));
        String json = objectMapper.writeValueAsString(BookDto.toDto(testBook));
        client.post()
                .uri("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(json)
                .exchange()
                .expectStatus().isCreated();

        verify(bookService, times(1)).save(any());
    }

    @Test
    void updateBookTest() throws Exception {
        Book testBook = TEST_BOOKS.get(0);
        given(bookService.update(any())).willReturn(Mono.just(testBook));
        String json = objectMapper.writeValueAsString(BookDto.toDto(testBook));
        client.put()
                .uri("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(json)
                .exchange()
                .expectStatus().isOk();

        verify(bookService, times(1)).update(any());
    }

    @Test
    void updateBookFailedTest() throws Exception {
        Book testBook = new Book(
                TEST_BOOKS.get(0).getName(),
                TEST_BOOKS.get(0).getAuthors(),
                TEST_BOOKS.get(0).getGenre());
        given(bookService.update(any())).willReturn(Mono.empty());
        String json = objectMapper.writeValueAsString(BookDto.toDto(testBook));
        client.put()
                .uri("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(json)
                .exchange()
                .expectStatus().isBadRequest();

        verify(bookService, times(1)).update(any());
    }

    @Test
    void deleteBookTest() throws Exception {
        String id = TEST_BOOKS.get(0).getId();
        given(bookService.deleteById(anyString())).willReturn(Mono.empty());
        client.delete()
                .uri(uriBuilder -> uriBuilder
                        .path("/books")
                        .queryParam("id", id)
                        .build())
                .exchange()
                .expectStatus().isOk();

        verify(bookService, times(1)).deleteById(id);
    }
}