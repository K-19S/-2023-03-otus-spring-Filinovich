package ru.otus.filinovich.resilince.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.filinovich.resilince.clients.AuthorClient;
import ru.otus.filinovich.resilince.dto.AuthorDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorClient client;

    @GetMapping("/authors")
    ResponseEntity<List> getAllBooks(@RequestHeader("Authorization") String authorization) {
        return client.getAllAuthors(authorization);
    }

    @PostMapping("/authors")
    public ResponseEntity<?> addNewAuthor(@RequestBody AuthorDto authorDto,
                                          @RequestHeader("Authorization") String authorization) {
        return client.addNewAuthor(authorDto, authorization);
    }

    @PutMapping("/authors")
    public ResponseEntity<?> updateGenre(@RequestBody AuthorDto authorDto,
                                         @RequestHeader("Authorization") String authorization) {
        return client.updateAuthor(authorDto, authorization);
    }

    @DeleteMapping("/authors")
    public ResponseEntity<?> deleteGenre(@RequestParam String id,
                                         @RequestHeader("Authorization") String authorization) {
        return client.deleteAuthor(id, authorization);
    }
}
