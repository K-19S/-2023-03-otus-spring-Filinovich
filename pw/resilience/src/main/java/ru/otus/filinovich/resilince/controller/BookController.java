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
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.filinovich.resilince.clients.BookClient;
import ru.otus.filinovich.resilince.dto.BookDto;
import ru.otus.filinovich.resilince.model.BookFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookClient bookClient;

    @GetMapping("/books")
    ResponseEntity<List> getAllBooks(@RequestHeader String authorization) {
        return bookClient.getAllBooks(authorization);
    }

    @GetMapping("/books/{bookId}/file")
    public ResponseEntity<BookFile> getBookFile(@PathVariable String bookId, @RequestHeader String authorization) {
        return bookClient.getBookFile(bookId, authorization);
    }

    @PostMapping("/books")
    public ResponseEntity<?> createNewBook(@RequestBody BookDto bookDto, @RequestHeader String authorization) {
        return bookClient.createNewBook(bookDto, authorization);
    }

    @PutMapping("/books")
    public ResponseEntity<?> updateBook(@RequestBody BookDto bookDto, @RequestHeader String authorization) {
        return bookClient.updateBook(bookDto, authorization);
    }

    @DeleteMapping("/books")
    public ResponseEntity<?> deleteBook(@RequestParam String id, @RequestHeader String authorization) {
        return bookClient.deleteBook(id, authorization);
    }
}
