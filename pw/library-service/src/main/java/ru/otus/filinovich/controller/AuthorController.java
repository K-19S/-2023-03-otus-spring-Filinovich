package ru.otus.filinovich.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.filinovich.dto.AuthorDto;
import ru.otus.filinovich.service.author.AuthorService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/authors")
    public ResponseEntity<List<AuthorDto>> getAllAuthors() {
        List<AuthorDto> allAuthors = authorService.getAllAuthors().stream()
                .map(AuthorDto::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(allAuthors);
    }
}
