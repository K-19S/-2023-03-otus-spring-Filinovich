package ru.otus.filinovich.dao.book_comments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.filinovich.domain.BookComment;

@Repository
public interface BookCommentRepository extends JpaRepository<BookComment, Long> {

}
