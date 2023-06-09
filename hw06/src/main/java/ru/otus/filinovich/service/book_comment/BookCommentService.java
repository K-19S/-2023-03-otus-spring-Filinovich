package ru.otus.filinovich.service.book_comment;

import ru.otus.filinovich.domain.BookComment;

import java.util.List;

public interface BookCommentService {

    List<BookComment> getAllCommentsByBookId(Long id);

    String getAllCommentsDescriptionWithIdByBookId(Long id);

    BookComment getCommentById(Long id);

    String getCommentDescriptionById(Long id);

    String getCommentDescription(BookComment bookComment);

    BookComment createComment();

    BookComment updateComment();

    String updateCommentAndGetDescription();

    boolean existsById(Long id);

    boolean deleteById(Long id);
}
