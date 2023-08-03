package ru.otus.filinovich.service.book_comment;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.filinovich.domain.BookComment;

public interface BookCommentService {

    Flux<BookComment> getAllCommentsByBookId(String id);

    Mono<BookComment> createComment(Mono<BookComment> comment, String bookId);

    Flux<BookComment> findByBookId(String bookId);

    Mono<Void> delete(Mono<BookComment> comment);
}
