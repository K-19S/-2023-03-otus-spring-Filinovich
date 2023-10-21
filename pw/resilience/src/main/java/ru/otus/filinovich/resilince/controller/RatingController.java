package ru.otus.filinovich.resilince.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.filinovich.resilince.clients.RatingClient;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RatingController {

    private final RatingClient client;

    @PostMapping("/setRating")
    public ResponseEntity<List> setRating(@RequestParam String bookId,
                                          @RequestParam Integer rating,
                                          @RequestHeader String authorization) {
        return client.setRating(bookId, rating, authorization);
    }
}
