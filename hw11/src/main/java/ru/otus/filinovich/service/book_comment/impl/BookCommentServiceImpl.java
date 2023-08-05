package ru.otus.filinovich.service.book_comment.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.filinovich.dao.book_comments.BookCommentRepository;
import ru.otus.filinovich.domain.BookComment;
import ru.otus.filinovich.service.book.BookService;
import ru.otus.filinovich.service.book_comment.BookCommentService;

@Service
@RequiredArgsConstructor
public class BookCommentServiceImpl implements BookCommentService {

    private final BookCommentRepository bookCommentRepository;

    private final BookService bookService;

    @Override
    public Flux<BookComment> getAllCommentsByBookId(String id) {
        return bookCommentRepository.findByBookId(id);
    }

    @Override
    public Mono<BookComment> createComment(Mono<BookComment> bookComment, String bookId) {

        return bookComment.flatMap(comment ->
            bookService.getBookById(bookId)
            .flatMap(book -> {
                comment.setBook(book);
                return bookCommentRepository.save(comment);
            })
        );
    }

    @Override
    public Flux<BookComment> findByBookId(String bookId) {
        return bookCommentRepository.findByBookId(bookId);
    }

    @Override
    public Mono<Void> delete(Mono<BookComment> comment) {
        return comment.flatMap(bookCommentRepository::delete);
    }
}
