package ru.otus.filinovich.resilince.clients;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.otus.filinovich.resilince.dto.GenreDto;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GenreClient extends AbstractClient {

    private final RestTemplate restTemplate;

    private final CircuitBreakerFactory circuitBreakerFactory;


    public ResponseEntity<List> getAllGenres(String authorization) {
        return circuitBreakerFactory.create("allGenres").run(
                () -> restTemplate.exchange("http://library/genres",
                        HttpMethod.GET, getEntity(authorization), List.class),
                throwable -> fallbackEmptyList());
    }

    public ResponseEntity<?> addNewGenre(GenreDto genreDto, String authorization) {
        return circuitBreakerFactory.create("createGenre").run(
                () -> restTemplate.exchange("http://library/genre",
                        HttpMethod.POST, getEntity(genreDto, authorization), Object.class),
                throwable -> fallbackEmptyList());
    }

    public ResponseEntity<?> updateGenre(GenreDto genreDto, String authorization) {
        return circuitBreakerFactory.create("updateGenre").run(
                () -> restTemplate.exchange("http://library/genre/" + genreDto.getId(),
                        HttpMethod.PUT, getEntity(genreDto, authorization), Object.class),
                throwable -> fallbackError());
    }

    public ResponseEntity<?> deleteGenre(String id, String authorization) {
        return circuitBreakerFactory.create("deleteGenre").run(
                () -> restTemplate.exchange("http://library/genre/" + id,
                        HttpMethod.DELETE, getEntity(authorization), Object.class),
                throwable -> fallbackError());
    }

    private ResponseEntity<List> fallbackEmptyList() {
        return ResponseEntity.ok(new ArrayList<>());
    }

    private ResponseEntity<?> fallbackError() {
        return ResponseEntity.status(429).build();
    }
}
