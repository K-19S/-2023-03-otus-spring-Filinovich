package ru.otus.filinovich.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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

    @PostMapping("/genres")
    public ResponseEntity<?> createNewGenre(@RequestBody GenreDto genreDto) {
        genreService.save(GenreDto.fromDto(genreDto));
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/genres")
    public ResponseEntity<?> updateGenre(@RequestBody GenreDto genreDto) {
        if (genreService.update(GenreDto.fromDto(genreDto))) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/genres")
    public ResponseEntity<?> deleteGenre(@RequestParam String id) {
        genreService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
