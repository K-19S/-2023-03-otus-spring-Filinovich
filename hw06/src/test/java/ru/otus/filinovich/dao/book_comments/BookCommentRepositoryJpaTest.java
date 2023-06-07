package ru.otus.filinovich.dao.book_comments;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.filinovich.dao.book_comments.impl.BookCommentRepositoryJpa;
import ru.otus.filinovich.domain.Book;
import ru.otus.filinovich.domain.BookComment;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(BookCommentRepositoryJpa.class)
class BookCommentRepositoryJpaTest {

    private static final Long CORRECT_COMMENT_ID = 1L;

    private static final Long CORRECT_BOOK_ID = 1L;

    private static final Long INCORRECT_COMMENT_ID = -100L;

    @Autowired
    private BookCommentRepositoryJpa bookCommentRepositoryJpa;

    @Autowired
    private TestEntityManager em;

    @Test
    public void getByCorrectIdTest() {
        var expectedId = CORRECT_COMMENT_ID;
        Optional<BookComment> expected = bookCommentRepositoryJpa.getById(expectedId);
        BookComment actual = em.find(BookComment.class, expectedId);
        assertThat(expected).isNotNull().get().isEqualTo(actual);
    }

    @Test
    public void getByIncorrectIdTest() {
        Optional<BookComment> expected = bookCommentRepositoryJpa.getById(INCORRECT_COMMENT_ID);
        assertThat(expected).isEqualTo(Optional.empty());
    }

    @Test
    public void createTest() {
        BookComment testComment = generateTestComment();
        bookCommentRepositoryJpa.create(testComment);
        em.flush();
        BookComment expected = em.find(BookComment.class, testComment.getId());
        assertThat(testComment).isEqualTo(expected);
    }

    @Test
    public void updateTest() {
        BookComment testComment = generateTestComment();
        em.persist(testComment);
        testComment.setText("TestText2");
        em.flush();

        BookComment expected = em.find(BookComment.class, testComment.getId());
        assertThat(testComment).isEqualTo(expected);
    }

    @Test
    public void deleteByCorrectIdTest() {
        bookCommentRepositoryJpa.deleteById(CORRECT_COMMENT_ID);
        BookComment comment = em.find(BookComment.class, CORRECT_COMMENT_ID);
        assertThat(comment).isNull();
    }

    @Test
    public void deleteByIncorrectIdTest() {
        bookCommentRepositoryJpa.deleteById(INCORRECT_COMMENT_ID);
    }

    private BookComment generateTestComment() {
        BookComment bookComment = new BookComment();
        bookComment.setText("TestComment");
        Book testBook = em.find(Book.class, CORRECT_BOOK_ID);
        testBook.setComments(List.of(bookComment));
        bookComment.setBook(testBook);
        return bookComment;
    }

}