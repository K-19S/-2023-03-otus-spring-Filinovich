package ru.otus.filinovich.service.book_comment;

import ru.otus.filinovich.domain.BookComment;

import java.util.List;

public interface BookCommentService {

    List<BookComment> getAllCommentsByBookId(String id);

    void createComment(BookComment comment, String bookId);

    List<BookComment> findByBookId(String bookId);

    void deleteAll(List<BookComment> comments);
}
