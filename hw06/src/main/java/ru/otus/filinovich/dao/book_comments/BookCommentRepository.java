package ru.otus.filinovich.dao.book_comments;

import ru.otus.filinovich.domain.BookComment;

import java.util.Optional;

public interface BookCommentRepository {

    Optional<BookComment> getById(Long id);

    BookComment create(BookComment bookComment);

    BookComment update(BookComment bookComment);

    void deleteById(Long id);
}
