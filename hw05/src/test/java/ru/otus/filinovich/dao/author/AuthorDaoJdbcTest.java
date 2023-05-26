package ru.otus.filinovich.dao.author;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.filinovich.domain.Author;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {

    private static List<Author> expectedAuthors;

    @Autowired
    private AuthorDaoJdbc authorDao;

    @BeforeAll
    public static void initAuthors() {
        expectedAuthors = new ArrayList<>();
        expectedAuthors.add(new Author(1L, "Agatha Christie"));
        expectedAuthors.add(new Author(2L, "James Patterson"));
        expectedAuthors.add(new Author(3L, "Stephen King"));
        expectedAuthors.add(new Author(4L, "Howard Lovecraft"));
        expectedAuthors.add(new Author(5L, "Diana Gabaldon"));
        expectedAuthors.add(new Author(6L, "Louis Lamour"));
        expectedAuthors.add(new Author(7L, "George R. R. Martin"));
        expectedAuthors.add(new Author(8L, "Joanne Rowling"));
    }

    @Test
    public void getAllTest() {
        List<Author> actual = authorDao.getAll();
        assertThat(actual).isEqualTo(expectedAuthors);
    }

    @Test
    public void getByIdCorrectTest() {
        Author actual = authorDao.getById(1L);
        Author expected = expectedAuthors.stream().filter(author -> author.getId() == 1L).findFirst().orElseThrow();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getByIdUncorrectBigTest() {
        Author actual = authorDao.getById(100L);
        assertThat(actual).isNull();
    }

    @Test
    public void getByIdUncorrectSmallTest() {
        Author actual = authorDao.getById(-100L);
        assertThat(actual).isNull();
    }


}