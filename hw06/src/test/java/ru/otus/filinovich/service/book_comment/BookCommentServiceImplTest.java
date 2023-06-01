package ru.otus.filinovich.service.book_comment;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.filinovich.dao.book_comments.BookCommentRepository;
import ru.otus.filinovich.domain.Author;
import ru.otus.filinovich.domain.Book;
import ru.otus.filinovich.domain.BookComment;
import ru.otus.filinovich.domain.Genre;
import ru.otus.filinovich.service.IOService;
import ru.otus.filinovich.service.book.BookService;
import ru.otus.filinovich.util.MessageProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Component
@ExtendWith(SpringExtension.class)
@Import(BookCommentServiceImpl.class)
class BookCommentServiceImplTest {

    private static List<BookComment> testComments;

    private static List<Book> testBooks;

    private static final String TEST_COMMENT_TEXT = "TestText1";

    private static final Long CORRECT_COMMENT_ID = 1L;

    private static final Long INCORRECT_COMMENT_ID = 100L;

    @Autowired
    private BookCommentServiceImpl bookCommentService;

    @MockBean
    private BookCommentRepository bookCommentRepository;

    @MockBean
    private BookService bookService;

    @MockBean
    private MessageProvider messageProvider;

    @MockBean
    private IOService ioService;

    @BeforeAll
    public static void initComments() {
        testBooks = new ArrayList<>(2);
        testBooks.add(
                new Book("TestBookName1",
                    List.of(new Author(1L, "TestAuthor1")),
                    new Genre(1L, "TestGenre1")));
        testBooks.add(
                new Book("TestBookName2",
                    List.of(new Author(2L, "TestAuthor2")),
                    new Genre(2L, "TestGenre2")));
        testComments = new ArrayList<>(2);
        testComments.add(new BookComment(1L, "TestText1", testBooks.get(0)));
        testComments.add(new BookComment(2L, "TestText2", testBooks.get(1)));
    }

    @Test
    public void getAllCommentsByBookIdTest() {
        given(bookCommentRepository.getByBookId(testBooks.get(0).getId())).willReturn(List.of(testComments.get(0)));
        List<BookComment> actual = bookCommentService.getAllCommentsByBookId(testBooks.get(0).getId());
        assertThat(actual).isEqualTo(List.of(testComments.get(0)));
        verify(bookCommentRepository, times(1)).getByBookId(testBooks.get(0).getId());
    }

    @Test
    public void getAllCommentsDescriptionWithIdByBookIdTest() {
        given(bookCommentRepository.getByBookId(testBooks.get(0).getId())).willReturn(List.of(testComments.get(0)));
        BookComment testComment = testComments.get(0);
        String expected = testComment.getBook().getName() + "\n" +
                testComment.getText() + ", ID: " + testComment.getId() + "\n";

        String actual = bookCommentService.getAllCommentsDescriptionWithIdByBookId(testBooks.get(0).getId());
        assertThat(actual).isEqualTo(expected);
        verify(bookCommentRepository, times(1)).getByBookId(testBooks.get(0).getId());
    }

    @Test
    public void getCommentByIdTest() {
        BookComment testComment = testComments.get(0);
        given(bookCommentRepository.getById(testComment.getId())).willReturn(Optional.of(testComment));
        BookComment actual = bookCommentService.getCommentById(testComment.getId());
        assertThat(actual).isEqualTo(testComment);
        verify(bookCommentRepository, times(1)).getById(testComment.getId());
    }

    @Test
    public void getCommentDescriptionByIdTest() {
        BookComment testComment = testComments.get(0);
        given(bookCommentRepository.getById(testComment.getId())).willReturn(Optional.of(testComment));
        String actual = bookCommentService.getCommentDescriptionById(testComment.getId());
        String expected = testComment.getBook().getName() + ", " + testComment.getText();
        assertThat(actual).isEqualTo(expected);
        verify(bookCommentRepository, times(1)).getById(testComment.getId());
    }

    @Test
    public void getCommentDescriptionTest() {
        BookComment testComment = testComments.get(0);
        String actual = bookCommentService.getCommentDescription(testComment);
        String expected = testComment.getBook().getName() + ", " + testComment.getText();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void createCommentTest() {
        given(bookService.getBookByIdWithPromptAll(any())).willReturn(testBooks.get(0));
        given(ioService.readStringWithPrompt(any())).willReturn(TEST_COMMENT_TEXT);
        BookComment comment = new BookComment();
        comment.setText(TEST_COMMENT_TEXT);
        comment.setBook(testBooks.get(0));
        bookCommentService.createComment();
        verify(bookService, times(1)).getBookByIdWithPromptAll(any());
        verify(ioService, times(1)).readStringWithPrompt(any());
        verify(bookCommentRepository, times(1)).create(comment);
    }

    @Test
    public void updateCommentTest() {
        BookComment testComment = testComments.get(0);
        given(bookCommentRepository.getById(CORRECT_COMMENT_ID)).willReturn(Optional.ofNullable(testComment));
        given(ioService.readLongWithPrompt(any())).willReturn(CORRECT_COMMENT_ID);
        given(ioService.readStringWithPrompt(any())).willReturn(TEST_COMMENT_TEXT);
        if (testComment != null) {
            testComment.setText(TEST_COMMENT_TEXT);
            testComment.setBook(testBooks.get(0));
        }
        bookCommentService.updateComment();
        verify(bookCommentRepository, times(1)).getById(CORRECT_COMMENT_ID);
        verify(ioService, times(1)).readLongWithPrompt(any());
        verify(bookCommentRepository, times(1)).update(testComment);
    }

    @Test
    public void updateCommentAndGetDescriptionTest() {
        BookComment testComment = testComments.get(0);
        given(bookCommentRepository.getById(CORRECT_COMMENT_ID)).willReturn(Optional.ofNullable(testComment));
        given(ioService.readLongWithPrompt(any())).willReturn(CORRECT_COMMENT_ID);
        given(ioService.readStringWithPrompt(any())).willReturn(TEST_COMMENT_TEXT);
        given(bookCommentRepository.update(testComment)).willReturn(testComment);
        if (testComment != null) {
            testComment.setText(TEST_COMMENT_TEXT);
            testComment.setBook(testBooks.get(0));
        }
        String expected = testComment.getBook().getName() + ", " + testComment.getText();
        String actual = bookCommentService.updateCommentAndGetDescription();
        assertThat(actual).isEqualTo(expected);
        verify(bookCommentRepository, times(1)).getById(CORRECT_COMMENT_ID);
        verify(ioService, times(1)).readLongWithPrompt(any());
        verify(bookCommentRepository, times(1)).update(testComment);
    }

    @Test
    public void existsByIdTest() {
        given(bookCommentRepository.getById(CORRECT_COMMENT_ID)).willReturn(Optional.of(testComments.get(0)));
        assertThat(bookCommentService.existsById(CORRECT_COMMENT_ID)).isTrue();
        given(bookCommentRepository.getById(INCORRECT_COMMENT_ID)).willReturn(Optional.empty());
        assertThat(bookCommentService.existsById(INCORRECT_COMMENT_ID)).isFalse();
    }

    @Test
    public void deleteById() {
        given(bookCommentRepository.getById(CORRECT_COMMENT_ID)).willReturn(Optional.of(testComments.get(0)));
        boolean correctDeleted = bookCommentService.deleteById(CORRECT_COMMENT_ID);
        assertThat(correctDeleted).isTrue();
        verify(bookCommentRepository, times(1)).getById(CORRECT_COMMENT_ID);
        verify(bookCommentRepository, times(1)).deleteById(CORRECT_COMMENT_ID);
        given(bookCommentRepository.getById(INCORRECT_COMMENT_ID)).willReturn(Optional.empty());
        boolean incorrectDeleted = bookCommentService.deleteById(INCORRECT_COMMENT_ID);
        assertThat(incorrectDeleted).isFalse();
        verify(bookCommentRepository, times(1)).getById(INCORRECT_COMMENT_ID);
    }
}