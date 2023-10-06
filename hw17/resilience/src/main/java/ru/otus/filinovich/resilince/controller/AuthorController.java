package ru.otus.filinovich.resilince.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    ResponseEntity<List> getAllBooks() {
        return client.getAllAuthors();
    }

    @PostMapping("/authors")
    public ResponseEntity<?> addNewAuthor(@RequestBody AuthorDto authorDto) {
        return client.addNewAuthor(authorDto);
    }

    @PutMapping("/authors")
    public ResponseEntity<?> updateGenre(@RequestBody AuthorDto authorDto) {
        return client.updateAuthor(authorDto);
    }

    @DeleteMapping("/authors")
    public ResponseEntity<?> deleteGenre(@RequestParam String id) {
        return client.deleteAuthor(id);
    }
}
