package ru.otus.filinovich.dao.genre;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.filinovich.domain.Genre;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {

    private static List<Genre> expectedGenres;

    @Autowired
    private GenreDaoJdbc genreDao;

    @BeforeAll
    public static void initGenres() {
        expectedGenres = new ArrayList<>();
        expectedGenres.add(new Genre(1L, "Mystery"));
        expectedGenres.add(new Genre(2L, "Thriller"));
        expectedGenres.add(new Genre(3L, "Horror"));
        expectedGenres.add(new Genre(4L, "Historical"));
        expectedGenres.add(new Genre(5L, "Western"));
        expectedGenres.add(new Genre(6L, "Fantasy"));
    }

    @Test
    public void getAllTest() {
        List<Genre> actual = genreDao.getAll();
        assertThat(actual).isEqualTo(expectedGenres);
    }

    @Test
    public void getByIdCorrectTest() {
        Genre actual = genreDao.getById(1L);
        Genre expected = expectedGenres.stream().filter(genre -> genre.getId() == 1L).findFirst().orElseThrow();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getByIdUncorrectBigTest() {
        Genre actual = genreDao.getById(100L);
        assertThat(actual).isNull();
    }

    @Test
    public void getByIfUncorrectSmallTest() {
        Genre actual = genreDao.getById(-100L);
        assertThat(actual).isNull();
    }
}