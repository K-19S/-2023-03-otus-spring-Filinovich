package ru.otus.filinovich.resilince.clients;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.otus.filinovich.resilince.dto.GenreDto;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GenreClient {

    private final RestTemplate restTemplate;

    private final CircuitBreakerFactory circuitBreakerFactory;


    public ResponseEntity<List> getAllGenres() {
        return circuitBreakerFactory.create("allGenres").run(
                () -> restTemplate.getForEntity("http://library/genres", List.class),
                throwable -> fallbackEmptyList());
    }

    public ResponseEntity<?> addNewGenre(GenreDto genreDto) {
        return circuitBreakerFactory.create("createGenre").run(
                () -> restTemplate.postForEntity("http://library/genre", genreDto, Object.class),
                throwable -> fallbackEmptyList());
    }

    public ResponseEntity<?> updateGenre(GenreDto genreDto) {
        HttpEntity<?> entity = new HttpEntity<>(genreDto);
        return circuitBreakerFactory.create("updateGenre").run(
                () -> restTemplate.exchange("http://library/genre/" + genreDto.getId(),
                        HttpMethod.PUT, entity, Object.class),
                throwable -> fallbackError());
    }

    public ResponseEntity<?> deleteGenre(String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity requestEntity = new HttpEntity<>(headers);
        return circuitBreakerFactory.create("deleteGenre").run(
                () -> restTemplate.exchange("http://library/genre/" + id,
                        HttpMethod.DELETE, requestEntity, Object.class),
                throwable -> fallbackError());
    }

    private ResponseEntity<List> fallbackEmptyList() {
        return ResponseEntity.ok(new ArrayList<>());
    }

    private ResponseEntity<?> fallbackError() {
        return ResponseEntity.status(429).build();
    }
}
