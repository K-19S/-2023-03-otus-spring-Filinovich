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
import ru.otus.filinovich.resilince.dto.BookDto;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BookClient {

    private final RestTemplate restTemplate;

    private final CircuitBreakerFactory circuitBreakerFactory;


    public ResponseEntity<List> getAllBooks() {
        return circuitBreakerFactory.create("allBooks").run(
                () -> restTemplate.getForEntity("http://library/books", List.class),
                throwable -> fallbackEmptyList());
    }

    public ResponseEntity<?> createNewBook(BookDto bookDto) {
        return circuitBreakerFactory.create("createBook").run(
                () -> restTemplate.postForEntity("http://library/books", bookDto, Object.class),
                throwable -> fallbackEmptyList());
    }

    public ResponseEntity<?> updateBook(BookDto bookDto) {
        HttpEntity<?> entity = new HttpEntity<>(bookDto);
        return circuitBreakerFactory.create("updateBook").run(
                () -> restTemplate.exchange("http://library/books", HttpMethod.PUT, entity, Object.class),
                throwable -> fallbackError());
    }

    public ResponseEntity<?> deleteBook(String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity requestEntity = new HttpEntity<>(headers);
        return circuitBreakerFactory.create("deleteBook").run(
                () -> restTemplate.exchange("http://library/book/" + id,
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
