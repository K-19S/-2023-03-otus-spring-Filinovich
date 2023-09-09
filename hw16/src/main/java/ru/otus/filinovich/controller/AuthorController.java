package ru.otus.filinovich.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
}
