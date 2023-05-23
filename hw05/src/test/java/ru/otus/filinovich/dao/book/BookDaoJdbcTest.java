package ru.otus.filinovich.dao.book;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.NullValueInNestedPathException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.filinovich.domain.Author;
import ru.otus.filinovich.domain.Book;
import ru.otus.filinovich.domain.Genre;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {

    private static List<Book> expectedBooks;

    @Autowired
    private BookDaoJdbc bookDao;

    @BeforeAll
    public static void initBooks() {
        expectedBooks = new ArrayList<>();
        expectedBooks.add(new Book(1L, "Ten Little Niggers",
                new Author(1L, "Agatha Christie"), new Genre(1L, "Mystery")));
        expectedBooks.add(new Book(2L, "The Murder House",
                new Author(2L, "James Patterson"), new Genre(2L, "Thriller")));
        expectedBooks.add(new Book(3L, "IT",
                new Author(3L, "Stephen King"), new Genre(3L, "Horror")));
        expectedBooks.add(new Book(4L, "At the Mountains of Madness",
                new Author(4L, "Howard Lovecraft"), new Genre(3L, "Horror")));
        expectedBooks.add(new Book(5L, "Lord John and the Private Matter",
                new Author(5L, "Diana Gabaldon"), new Genre(4L, "Historical")));
        expectedBooks.add(new Book(6L, "Sackett",
                new Author(6L, "Louis Lamour"), new Genre(5L, "Western")));
        expectedBooks.add(new Book(7L, "A Game of Thrones",
                new Author(7L, "George R. R. Martin"), new Genre(6L, "Fantasy")));
        expectedBooks.add(new Book(8L, "Harry Potter and the Deathly Hallows",
                new Author(8L, "Joanne Rowling"), new Genre(6L, "Fantasy")));
    }

    @Test
    public void getAllTest() {
        List<Book> actual = bookDao.getAll();
        assertThat(actual).isEqualTo(expectedBooks);
    }

    @Test
    public void getByIdCorrectTest() {
        Book actual = bookDao.getById(2L);
        Book expected = expectedBooks.stream().filter(book -> book.getId() == 2L).findFirst().orElseThrow();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getByIdUncorrectBigTest() {
        Book actual = bookDao.getById(100L);
        assertThat(actual).isNull();
    }

    @Test
    public void getByIdUncorrectSmallTest() {
        Book actual = bookDao.getById(-100L);
        assertThat(actual).isNull();
    }

    @Test
    public void existByIdCorrectTest() {
        boolean actual = bookDao.existById(3L);
        assertThat(actual).isTrue();
    }

    @Test
    public void existByIdUncorrectBigTest() {
        boolean actual = bookDao.existById(100L);
        assertThat(actual).isFalse();
    }

    @Test
    public void existByIdUncorrectSmallTest() {
        boolean actual = bookDao.existById(-100L);
        assertThat(actual).isFalse();
    }

    @Test
    public void createCorrectTest() {
        Genre expectedGenre = new Genre(3L, "Horror");
        Author expectedAuthor = new Author(3L, "Stephen King");
        Book expectedBook = new Book("TestName", expectedAuthor, expectedGenre);
        Long actualIdCreatedBook = bookDao.create(expectedBook);
        expectedBook.setId(actualIdCreatedBook);
        Book actual = bookDao.getById(actualIdCreatedBook);
        assertThat(actual).isEqualTo(expectedBook);
        List<Book> actualBooksList = bookDao.getAll();
        assertThat(actualBooksList).contains(expectedBook);
    }

    @Test
    public void createNullTest() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> bookDao.create(null));
    }

    @Test
    public void createEmptyTest() {
        assertThatExceptionOfType(NullValueInNestedPathException.class)
                .isThrownBy(() -> bookDao.create(new Book()));
    }

    @Test
    public void updateCorrectTest() {
        Genre expectedGenre = new Genre(3L, "Horror");
        Author expectedAuthor = new Author(3L, "Stephen King");
        Book expectedBook = new Book(3L, "TestName", expectedAuthor, expectedGenre);
        bookDao.update(expectedBook);
        Book actual = bookDao.getById(expectedBook.getId());
        assertThat(actual).isEqualTo(expectedBook);
        List<Book> actualBooksList = bookDao.getAll();
        assertThat(actualBooksList).contains(expectedBook);
    }

    @Test
    public void updateNullTest() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> bookDao.update(null));
    }

    @Test
    public void updateEmptyTest() {
        assertThatExceptionOfType(NullValueInNestedPathException.class)
                .isThrownBy(() -> bookDao.update(new Book()));
    }

    @Test
    public void deleteByIdCorrectTest() {
        List<Book> testListBook = new ArrayList<>(expectedBooks);
        assertThat(testListBook).isEqualTo(bookDao.getAll());
        Book deletedBook = testListBook.get(3);
        testListBook.remove(deletedBook);
        bookDao.deleteById(deletedBook.getId());
        assertThat(testListBook).isEqualTo(bookDao.getAll());
    }

    @Test
    public void deleteByIdUncorrectTest() {
        bookDao.deleteById(100L);
        assertThat(expectedBooks).isEqualTo(bookDao.getAll());
    }

}