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
import ru.otus.filinovich.resilince.dto.AuthorDto;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthorClient {

    private final RestTemplate restTemplate;

    private final CircuitBreakerFactory circuitBreakerFactory;


    public ResponseEntity<List> getAllAuthors() {
        return circuitBreakerFactory.create("allAuthors").run(
                () -> restTemplate.getForEntity("http://library/authors", List.class),
                throwable -> fallbackEmptyList());
    }

    public ResponseEntity<?> addNewAuthor(AuthorDto authorDto) {
        return circuitBreakerFactory.create("createAuthor").run(
                () -> restTemplate.postForEntity("http://library/author", authorDto, Object.class),
                throwable -> fallbackEmptyList());
    }

    public ResponseEntity<?> updateAuthor(AuthorDto authorDto) {
        HttpEntity<?> entity = new HttpEntity<>(authorDto);
        return circuitBreakerFactory.create("updateAuthor").run(
                () -> restTemplate.exchange("http://library/author/" + authorDto.getId(),
                        HttpMethod.PUT, entity, Object.class),
                throwable -> fallbackError());
    }

    public ResponseEntity<?> deleteAuthor(String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity requestEntity = new HttpEntity<>(headers);
        return circuitBreakerFactory.create("deleteAuthor").run(
                () -> restTemplate.exchange("http://library/author/" + id,
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
