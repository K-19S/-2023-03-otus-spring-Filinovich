package ru.otus.filinovich.dao.book_comments;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.otus.filinovich.domain.BookComment;

public interface BookCommentRepository extends ReactiveMongoRepository<BookComment, String> {

    Flux<BookComment> findByBookId(String id);
}
