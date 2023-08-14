package ru.otus.filinovich.service.book_comment;

import org.springframework.security.access.prepost.PreAuthorize;
import ru.otus.filinovich.domain.BookComment;

import java.util.List;

public interface BookCommentService {

    List<BookComment> getAllCommentsByBookId(Long id);

    @PreAuthorize("hasRole('USER')")
    void createComment(BookComment comment, Long bookId);
}
