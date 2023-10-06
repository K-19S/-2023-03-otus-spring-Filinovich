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
import ru.otus.filinovich.resilince.clients.BookClient;
import ru.otus.filinovich.resilince.dto.BookDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookClient bookClient;

    @GetMapping("/books")
    ResponseEntity<List> getAllBooks() {
        return bookClient.getAllBooks();
    }

    @PostMapping("/books")
    public ResponseEntity<?> createNewBook(@RequestBody BookDto bookDto) {
        return bookClient.createNewBook(bookDto);
    }

    @PutMapping("/books")
    public ResponseEntity<?> updateBook(@RequestBody BookDto bookDto) {
        return bookClient.updateBook(bookDto);
    }

    @DeleteMapping("/books")
    public ResponseEntity<?> deleteBook(@RequestParam String id) {
        return bookClient.deleteBook(id);
    }
}
