package ru.otus.filinovich.resilince.clients;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.otus.filinovich.resilince.dto.BookDto;
import ru.otus.filinovich.resilince.model.BookFile;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BookClient extends AbstractClient {

    private final RestTemplate restTemplate;

    private final CircuitBreakerFactory circuitBreakerFactory;


    public ResponseEntity<List> getAllBooks(String authorization) {
        return circuitBreakerFactory.create("allBooks").run(
                () -> restTemplate.exchange("http://library/books",
                        HttpMethod.GET, getEntity(authorization), List.class),
                throwable -> fallbackEmptyList());
    }

    public ResponseEntity<BookFile> getBookFile(String bookId, String authorization) {
        return circuitBreakerFactory.create("getBookFile").run(
                () -> restTemplate.exchange("http://library/books/" + bookId + "/file",
                        HttpMethod.GET, getEntity(authorization), BookFile.class),
                throwable -> ResponseEntity.badRequest().build());
    }

    public ResponseEntity<?> createNewBook(BookDto bookDto, String authorization) {
        return circuitBreakerFactory.create("createBook").run(
                () -> restTemplate.exchange("http://library/books",
                        HttpMethod.POST, getEntity(bookDto, authorization), Object.class),
                throwable -> fallbackEmptyList());
    }

    public ResponseEntity<?> updateBook(BookDto bookDto, String authorization) {
        return circuitBreakerFactory.create("updateBook").run(
                () -> restTemplate.exchange("http://library/books",
                        HttpMethod.PUT, getEntity(bookDto, authorization), Object.class),
                throwable -> fallbackError());
    }

    public ResponseEntity<?> deleteBook(String id, String authorization) {
        return circuitBreakerFactory.create("deleteBook").run(
                () -> restTemplate.exchange("http://library/book/" + id,
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
