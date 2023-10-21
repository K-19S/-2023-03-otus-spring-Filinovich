package ru.otus.filinovich.resilince.clients;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RatingClient extends AbstractClient {

    private final RestTemplate restTemplate;

    private final CircuitBreakerFactory circuitBreakerFactory;

    public ResponseEntity<List> setRating(String bookId, Integer rating, String authorization) {
        return circuitBreakerFactory.create("setRating").run(
                () -> restTemplate.exchange("http://library/setRating?bookId=" + bookId + "&rating=" + rating,
                        HttpMethod.POST, getEntity(authorization), List.class),
                throwable -> fallbackList());
    }

    private ResponseEntity<List> fallbackList() {
        return ResponseEntity.badRequest().body(new ArrayList<>());
    }
}
