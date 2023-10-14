package ru.otus.filinovich.dao.rating;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.filinovich.domain.Book;
import ru.otus.filinovich.domain.Rating;
import ru.otus.filinovich.domain.User;

import java.util.List;

@Repository
public interface RatingRepository extends MongoRepository<Rating, String> {

    Rating getByUserAndBook(User user, Book book);

    List<Rating> getAllByBook(Book book);
}
