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
import ru.otus.filinovich.domain.Genre;
import ru.otus.filinovich.dto.GenreDto;
import ru.otus.filinovich.service.genre.GenreService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class GenreConfigTest {

    private static final List<Genre> TEST_GENRES = new ArrayList<>();

    @Autowired
    private RouterFunction<ServerResponse> genreRouters;

    @MockBean
    private GenreService genreService;

    @Autowired
    private ObjectMapper objectMapper;

    private WebTestClient client;

    @BeforeAll
    public static void initTestGenres() {
        Genre genre1 = new Genre("Genre1");
        genre1.setId("64c50941ddce6b0b5c91b711");
        TEST_GENRES.add(genre1);

        Genre genre2 = new Genre("Genre2");
        genre2.setId("64c50941ddce6b0b5c91b712");
        TEST_GENRES.add(genre2);
    }

    @BeforeEach
    public void initWebTestClient() {
        client = WebTestClient
                .bindToRouterFunction(genreRouters)
                .build();
    }

    @Test
    void getAllGenresTest() throws Exception {
        given(genreService.getAllGenres()).willReturn(Flux.fromIterable(TEST_GENRES));

        String json = objectMapper.writeValueAsString(TEST_GENRES.stream().map(GenreDto::toDto).toList());
        client.get()
                .uri("/genres")
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(json);

        verify(genreService, times(1)).getAllGenres();
    }

    @Test
    void createNewGenreTest() throws Exception {
        Genre testGenre = TEST_GENRES.get(0);
        given(genreService.save(any())).willReturn(Mono.just(testGenre));
        String json = objectMapper.writeValueAsString(GenreDto.toDto(testGenre));
        client.post()
                .uri("/genres")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(json)
                .exchange()
                .expectStatus().isCreated();

        verify(genreService, times(1)).save(any());
    }

    @Test
    void updateGenreTest() throws Exception {
        Genre testGenre = TEST_GENRES.get(0);
        given(genreService.update(any())).willReturn(Mono.just(testGenre));
        String json = objectMapper.writeValueAsString(GenreDto.toDto(testGenre));
        client.put()
                .uri("/genres")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(json)
                .exchange()
                .expectStatus().isOk();

        verify(genreService, times(1)).update(any());
    }

    @Test
    void updateGenreFailedTest() throws Exception {
        Genre testGenre = new Genre(TEST_GENRES.get(0).getName());
        given(genreService.update(any())).willReturn(Mono.empty());
        String json = objectMapper.writeValueAsString(GenreDto.toDto(testGenre));
        client.put()
                .uri("/genres")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(json)
                .exchange()
                .expectStatus().isBadRequest();


        verify(genreService, times(1)).update(any());
    }

    @Test
    void deleteGenreTest() throws Exception {
        String id = TEST_GENRES.get(0).getId();
        given(genreService.deleteById(anyString())).willReturn(Mono.empty());
        client.delete()
                .uri(uriBuilder -> uriBuilder
                        .path("/genres")
                        .queryParam("id", id)
                        .build())
                .exchange()
                .expectStatus().isOk();

        verify(genreService, times(1)).deleteById(id);
    }
}