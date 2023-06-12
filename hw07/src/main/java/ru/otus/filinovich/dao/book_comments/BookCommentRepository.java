package ru.otus.filinovich.dao.book_comments;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.filinovich.domain.BookComment;

public interface BookCommentRepository extends JpaRepository<BookComment, Long> {

}
