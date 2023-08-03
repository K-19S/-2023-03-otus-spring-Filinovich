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
import ru.otus.filinovich.dto.AuthorDto;
import ru.otus.filinovich.service.author.AuthorService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class AuthorConfigTest {

    private static final List<Author> TEST_AUTHORS = new ArrayList<>();

    @Autowired
    private RouterFunction<ServerResponse> authorRouters;

    @MockBean
    private AuthorService authorService;

    @Autowired
    private ObjectMapper objectMapper;

    private WebTestClient client;

    @BeforeAll
    public static void initTestAuthors() {
        Author author1 = new Author("Author1");
        author1.setId("64c50941ddce6b0b5c91b711");
        TEST_AUTHORS.add(author1);

        Author author2 = new Author("Author2");
        author2.setId("64c50941ddce6b0b5c91b712");
        TEST_AUTHORS.add(author2);
    }

    @BeforeEach
    public void initWebTestClient() {
        client = WebTestClient
                .bindToRouterFunction(authorRouters)
                .build();
    }

    @Test
    void getAllAuthorsTest() throws Exception {

        given(authorService.getAllAuthors()).willReturn(Flux.fromIterable(TEST_AUTHORS));
        String json = objectMapper.writeValueAsString(TEST_AUTHORS.stream().map(AuthorDto::toDto).toList());

        client.get()
            .uri("/authors")
            .exchange()
                .expectStatus().isOk()
                .expectBody().json(json);

        verify(authorService, times(1)).getAllAuthors();
    }

    @Test
    void createNewAuthorTest() throws Exception {
        Author testAuthor = TEST_AUTHORS.get(0);
        given(authorService.save(any())).willReturn(Mono.just(testAuthor));
        String json = objectMapper.writeValueAsString(AuthorDto.toDto(testAuthor));
        client.post()
            .uri("/authors")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(json)
            .exchange()
                .expectStatus().isCreated();

        verify(authorService, times(1)).save(any());
    }

    @Test
    void updateAuthorTest() throws Exception {
        Author testAuthor = TEST_AUTHORS.get(0);
        given(authorService.update(any())).willReturn(Mono.just(testAuthor));
        String json = objectMapper.writeValueAsString(AuthorDto.toDto(testAuthor));
        client.put()
                .uri("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(json)
                .exchange()
                .expectStatus().isOk();

        verify(authorService, times(1)).update(any());
    }

    @Test
    void updateAuthorFailedTest() throws Exception {
        Author testAuthor = new Author(TEST_AUTHORS.get(0).getName());
        given(authorService.update(any())).willReturn(Mono.empty());
        String json = objectMapper.writeValueAsString(AuthorDto.toDto(testAuthor));
        client.put()
                .uri("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(json)
                .exchange()
                .expectStatus().isBadRequest();

        verify(authorService, times(1)).update(any());
    }

    @Test
    void deleteAuthorTest() throws Exception {
        String id = TEST_AUTHORS.get(0).getId();
        given(authorService.deleteById(anyString())).willReturn(Mono.empty());
        client.delete()
                .uri(uriBuilder -> uriBuilder
                    .path("/authors")
                    .queryParam("id", id)
                    .build())
                .exchange()
                .expectStatus().isOk();

        verify(authorService, times(1)).deleteById(id);
    }
}