package ru.otus.filinovich.service.rating;

import ru.otus.filinovich.domain.Book;
import ru.otus.filinovich.domain.Rating;
import ru.otus.filinovich.domain.User;
import ru.otus.filinovich.dto.BookWithRatingDto;

import java.util.List;

public interface RatingService {

    List<BookWithRatingDto> setRating(String bookId, Integer rating, User user);

    Rating getRatingByUserBook(User user, Book book);

    Double getRatingByBook(Book book);
}
