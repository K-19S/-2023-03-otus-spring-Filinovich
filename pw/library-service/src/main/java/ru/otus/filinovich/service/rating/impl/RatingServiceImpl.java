package ru.otus.filinovich.service.rating.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.filinovich.dao.rating.RatingRepository;
import ru.otus.filinovich.domain.Book;
import ru.otus.filinovich.domain.Rating;
import ru.otus.filinovich.domain.User;
import ru.otus.filinovich.dto.BookDto;
import ru.otus.filinovich.dto.BookWithRatingDto;
import ru.otus.filinovich.service.book.BookService;
import ru.otus.filinovich.service.rating.RatingService;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final BookService bookService;

    private final RatingRepository ratingRepository;

    @Override
    public List<BookWithRatingDto> setRating(String bookId, Integer rating, User user) {
        Book book = bookService.getBookById(bookId);
        Rating entityRating = new Rating();
        entityRating.setRating(rating);
        entityRating.setUser(user);
        entityRating.setBook(book);
        ratingRepository.save(entityRating);
        List<BookWithRatingDto> response = new ArrayList<>();
        for (Book book1 : user.getBooks()) {
            Double ratingByBook = getRatingByBook(book1);
            BookDto bookDto = BookDto.toDto(book1);
            bookDto.setRating(ratingByBook);
            Rating rating1 = getRatingByUserBook(user, book1);
            BookWithRatingDto dto = new BookWithRatingDto(bookDto, rating1);
            response.add(dto);
        }
        return response;
    }

    @Override
    public Rating getRatingByUserBook(User user, Book book) {
        return ratingRepository.getByUserAndBook(user, book);
    }

    @Override
    public Double getRatingByBook(Book book) {
        OptionalDouble average = ratingRepository.getAllByBook(book).stream()
                .mapToDouble(rating -> rating.getRating().doubleValue())
                .average();
        return average.isPresent() ? average.getAsDouble() : 0d;
    }
}
