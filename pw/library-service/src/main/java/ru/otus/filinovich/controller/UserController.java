package ru.otus.filinovich.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.filinovich.domain.Book;
import ru.otus.filinovich.domain.Rating;
import ru.otus.filinovich.domain.User;
import ru.otus.filinovich.dto.BookDto;
import ru.otus.filinovich.dto.BookWithRatingDto;
import ru.otus.filinovich.security.service.UserDetailsImpl;
import ru.otus.filinovich.service.book.BookService;
import ru.otus.filinovich.service.rating.RatingService;
import ru.otus.filinovich.service.user.UserService;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final BookService bookService;

    private final UserService userService;

    private final RatingService ratingService;

    @GetMapping("/userBooks")
    public ResponseEntity<Set<BookWithRatingDto>> getUserBooks(Authentication authentication) {
        User user = userService.getUserByUsername(((UserDetailsImpl) authentication.getPrincipal()).getUsername());
        Set<Book> books = ((UserDetailsImpl) authentication.getPrincipal()).getBooks();
        Set<BookWithRatingDto> set = new HashSet<>();
        for (Book book : books) {
            Double ratingByBook = ratingService.getRatingByBook(book);
            BookDto bookDto = BookDto.toDto(book);
            bookDto.setRating(ratingByBook);
            Rating rating = ratingService.getRatingByUserBook(user, book);
            set.add(new BookWithRatingDto(bookDto, rating));
        }
        return ResponseEntity.ok(set);
    }

    @PostMapping("/addUserbook")
    public ResponseEntity<Set<BookWithRatingDto>> addUserbook(@RequestParam String bookId,
                                                              Authentication authentication) {
        User user = userService.getUserByUsername(((UserDetailsImpl) authentication.getPrincipal()).getUsername());
        Book book = bookService.getBookById(bookId);
        user.getBooks().add(book);
        userService.save(user);
        Set<BookWithRatingDto> set = new HashSet<>();
        for (Book book1 : user.getBooks()) {
            Double ratingByBook = ratingService.getRatingByBook(book1);
            BookDto bookDto = BookDto.toDto(book1);
            bookDto.setRating(ratingByBook);
            Rating rating = ratingService.getRatingByUserBook(user, book);
            set.add(new BookWithRatingDto(bookDto, rating));
        }
        return ResponseEntity.ok(set);
    }

    @DeleteMapping("/deleteUserBook")
    public ResponseEntity<Set<BookWithRatingDto>> deleteUserBook(@RequestParam String id,
                                                                 Authentication authentication) {
        User user = userService.getUserByUsername(((UserDetailsImpl) authentication.getPrincipal()).getUsername());
        Set<BookWithRatingDto> newSetBooks = new HashSet<>();
        Set<Book> setBooks = new HashSet<>();
        for (Book book : user.getBooks()) {
            if (!book.getId().equals(id)) {
                setBooks.add(book);
                Double ratingByBook = ratingService.getRatingByBook(book);
                BookDto bookDto = BookDto.toDto(book);
                bookDto.setRating(ratingByBook);
                Rating rating = ratingService.getRatingByUserBook(user, book);
                newSetBooks.add(new BookWithRatingDto(bookDto, rating));
            }
        }
        user.setBooks(setBooks);
        user = userService.save(user);
        return ResponseEntity.ok(newSetBooks);
    }
}
