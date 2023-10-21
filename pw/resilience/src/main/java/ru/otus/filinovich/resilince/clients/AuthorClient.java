package ru.otus.filinovich.resilince.clients;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.otus.filinovich.resilince.dto.AuthorDto;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthorClient extends AbstractClient {

    private final RestTemplate restTemplate;

    private final CircuitBreakerFactory circuitBreakerFactory;


    public ResponseEntity<List> getAllAuthors(String authorization) {

        return circuitBreakerFactory.create("allAuthors").run(
                () -> restTemplate.exchange("http://library/authors",
                        HttpMethod.GET, getEntity(authorization), List.class),
                throwable -> fallbackEmptyList());
    }

    public ResponseEntity<?> addNewAuthor(AuthorDto authorDto, String authorization) {
        return circuitBreakerFactory.create("createAuthor").run(
                () -> restTemplate.exchange("http://library/author",
                        HttpMethod.POST, getEntity(authorDto, authorization), Object.class),
                throwable -> fallbackEmptyList());
    }

    public ResponseEntity<?> updateAuthor(AuthorDto authorDto, String authorization) {
        return circuitBreakerFactory.create("updateAuthor").run(
                () -> restTemplate.exchange("http://library/author/" + authorDto.getId(),
                        HttpMethod.PUT, getEntity(authorDto, authorization), Object.class),
                throwable -> fallbackError());
    }

    public ResponseEntity<?> deleteAuthor(String id, String authorization) {
        return circuitBreakerFactory.create("deleteAuthor").run(
                () -> restTemplate.exchange("http://library/author/" + id,
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
