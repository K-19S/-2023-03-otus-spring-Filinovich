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
import ru.otus.filinovich.resilince.clients.GenreClient;
import ru.otus.filinovich.resilince.dto.GenreDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GenreController {
    private final GenreClient client;

    @GetMapping("/genres")
    public ResponseEntity<List> getAllGenres() {
        return client.getAllGenres();
    }

    @PostMapping("/genres")
    public ResponseEntity<?> addNewGenre(@RequestBody GenreDto genreDto) {
        return client.addNewGenre(genreDto);
    }

    @PutMapping("/genres")
    public ResponseEntity<?> updateGenre(@RequestBody GenreDto genreDto) {
        return client.updateGenre(genreDto);
    }

    @DeleteMapping("/genres")
    public ResponseEntity<?> deleteGenre(@RequestParam String id) {
        return client.deleteGenre(id);
    }
}
