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
import ru.otus.filinovich.dto.AuthorDto;
import ru.otus.filinovich.service.author.AuthorService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/authors")
    public ResponseEntity<List<AuthorDto>> getAllAuthors() {
        List<AuthorDto> allAuthors = authorService.getAllAuthors().stream().map(AuthorDto::toDto).toList();
        return ResponseEntity.ok(allAuthors);
    }

    @PostMapping("/authors")
    public ResponseEntity<?> createNewAuthor(@RequestBody AuthorDto authorDto) {
        authorService.save(AuthorDto.fromDto(authorDto));
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/authors")
    public ResponseEntity<?> updateAuthor(@RequestBody AuthorDto authorDto) {
        if (authorService.update(AuthorDto.fromDto(authorDto))) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/authors")
    public ResponseEntity<?> deleteAuthor(@RequestParam String id) {
        authorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
