package ru.otus.filinovich.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.filinovich.domain.Book;
import ru.otus.filinovich.domain.BookFile;
import ru.otus.filinovich.dto.BookDto;
import ru.otus.filinovich.service.book.BookService;
import ru.otus.filinovich.service.files.FileService;
import ru.otus.filinovich.service.rating.RatingService;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final RatingService ratingService;

    private final FileService fileService;

    @GetMapping("/books")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookDto> allBooks = bookService.getAllBooks().stream()
                .map(book -> {
                    Double rating = ratingService.getRatingByBook(book);
                    BookDto dto = BookDto.toDto(book);
                    dto.setRating(rating);
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(allBooks);
    }

    @GetMapping("/books/{bookId}/file")
    public ResponseEntity<BookFile> getBookFile(@PathVariable String bookId) {
        Book book = bookService.getBookById(bookId);
        BookFile file = fileService.getByBook(book);
        byte[] decode = Base64.getDecoder().decode(file.getBytes().getBytes());
        file.setBytes(Arrays.toString(decode));
        return ResponseEntity.ok(fileService.getByBook(book));
    }

    @PostMapping("/books")
    public ResponseEntity<?> createNewBook(@RequestBody BookDto bookDto) {
        Book savedBook = bookService.save(BookDto.fromDto(bookDto));
        BookFile bookFile = new BookFile();
        bookFile.setBook(savedBook);
        bookFile.setName(bookDto.getFileName());
        bookFile.setBytes(bookDto.getFileBytes());
        fileService.save(bookFile);
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
