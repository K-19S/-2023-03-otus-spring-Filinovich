package ru.otus.filinovich.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.filinovich.dto.GenreDto;
import ru.otus.filinovich.service.genre.GenreService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/genres")
    public ResponseEntity<List<GenreDto>> getAllGenres() {
        List<GenreDto> allGenres = genreService.getAllGenres().stream().map(GenreDto::toDto).toList();
        return ResponseEntity.ok(allGenres);
    }
}
