package ru.otus.filinovich.dao.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.filinovich.domain.Author;
import ru.otus.filinovich.domain.Book;
import ru.otus.filinovich.domain.BookComment;
import ru.otus.filinovich.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(BookRepositoryJpa.class)
class BookRepositoryJpaTest {

    private static final Long CORRECT_BOOK_ID = 1L;

    private static final Long INCORRECT_BOOK_ID = -100L;

    private static final Integer CORRECT_BOOKS_SIZE = 8;

    @Autowired
    private BookRepositoryJpa bookRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    public void getAllTest() {
        List<Book> books = bookRepository.getAll();
        assertThat(books).isNotNull().hasSize(CORRECT_BOOKS_SIZE)
                .allMatch(book -> !book.getName().isBlank())
                .allMatch(book -> book.getGenre() != null)
                .allMatch(book -> book.getAuthors() != null && book.getAuthors().size() > 0)
                .allMatch(book -> book.getComments() != null && book.getComments().size() > 0);
    }

    @Test
    public void getByCorrectIdTest() {
        var expectedId = CORRECT_BOOK_ID;
        Optional<Book> expected = bookRepository.getById(expectedId);
        Book actual = em.find(Book.class, expectedId);
        assertThat(expected).isNotNull().get().isEqualTo(actual);
    }

    @Test
    public void getByIncorrectIdTest() {
        Optional<Book> expected = bookRepository.getById(INCORRECT_BOOK_ID);
        assertThat(expected).isEqualTo(Optional.empty());
    }

    @Test
    public void existsByCorrectIdTest() {
        assertThat(bookRepository.existById(CORRECT_BOOK_ID)).isTrue();
    }

    @Test
    public void existsByIncorrectIdTest() {
        assertThat(bookRepository.existById(INCORRECT_BOOK_ID)).isFalse();
    }

    @Test
    public void createTest() {
        Book testBook = generateTestBook();
        bookRepository.create(testBook);
        em.flush();
        Book expected = em.find(Book.class, testBook.getId());
        assertThat(testBook).isEqualTo(expected);
    }

    @Test
    public void updateTest() {
        Book testBook = generateTestBook();
        em.persist(testBook);
        testBook.setName("TestName2");
        Genre newGenre = new Genre();
        newGenre.setName("TestGenre2");
        testBook.setGenre(newGenre);
        Author newAuthor = new Author();
        newAuthor.setName("TestAuthor2");
        testBook.setAuthors(List.of(newAuthor));
        em.flush();

        Book expected = em.find(Book.class, testBook.getId());
        assertThat(testBook).isEqualTo(expected);
    }

    @Test
    public void deleteByCorrectIdTest() {
        bookRepository.deleteById(CORRECT_BOOK_ID);
        Book book = em.find(Book.class, CORRECT_BOOK_ID);
        assertThat(book).isNull();
    }

    @Test
    public void deleteByIncorrectIdTest() {
        bookRepository.deleteById(INCORRECT_BOOK_ID);
    }

    private Book generateTestBook() {
        Book testBook = new Book();
        testBook.setName("TestName");
        Genre genre = new Genre();
        genre.setName("TestGenreName");
        testBook.setGenre(genre);
        Author author = new Author();
        author.setName("TestAuthorName");
        testBook.setAuthors(List.of(author));
        BookComment bookComment = new BookComment();
        bookComment.setBook(testBook);
        bookComment.setText("TestComment");
        testBook.setComments(List.of(bookComment));
        return testBook;
    }

}