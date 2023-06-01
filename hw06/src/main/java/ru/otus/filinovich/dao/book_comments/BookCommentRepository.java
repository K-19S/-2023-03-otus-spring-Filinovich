package ru.otus.filinovich.dao.book_comments;

import ru.otus.filinovich.domain.BookComment;

import java.util.List;
import java.util.Optional;

public interface BookCommentRepository {

    List<BookComment> getByBookId(Long id);

    Optional<BookComment> getById(Long id);

    BookComment create(BookComment bookComment);

    BookComment update(BookComment bookComment);

    void deleteById(Long id);
}
