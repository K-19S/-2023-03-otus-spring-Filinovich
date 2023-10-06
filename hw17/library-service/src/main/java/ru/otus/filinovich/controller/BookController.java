package ru.otus.filinovich.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.otus.filinovich.dto.BookDto;
import ru.otus.filinovich.service.book.BookService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/books")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookDto> allBooks = bookService.getAllBooks().stream().map(BookDto::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(allBooks);
    }

    @PostMapping("/books")
    public ResponseEntity<?> createNewBook(@RequestBody BookDto bookDto) {
        bookService.save(BookDto.fromDto(bookDto));
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/books")
    public ResponseEntity<?> updateBook(@RequestBody BookDto bookDto) {
        if (bookService.update(BookDto.fromDto(bookDto))) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
