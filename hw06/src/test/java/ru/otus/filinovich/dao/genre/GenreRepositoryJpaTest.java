package ru.otus.filinovich.dao.genre;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.filinovich.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(GenreRepositoryJpa.class)
class GenreRepositoryJpaTest {

    private static final Long CORRECT_GENRE_ID = 1L;

    private static final Long INCORRECT_GENRE_ID = -100L;

    private static final Integer CORRECT_GENRE_SIZE = 6;

    @Autowired
    private GenreRepositoryJpa genreRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    public void getAllTest() {
        List<Genre> genres = genreRepository.getAll();
        assertThat(genres).isNotNull().hasSize(CORRECT_GENRE_SIZE)
                .allMatch(genre -> !genre.getName().isBlank());
    }

    @Test
    public void getByCorrectIdTest() {
        var expectedId = CORRECT_GENRE_ID;
        Optional<Genre> expected = genreRepository.getById(expectedId);
        Genre actual = em.find(Genre.class, expectedId);
        assertThat(expected).isNotNull().get().isEqualTo(actual);
    }

    @Test
    public void getByIncorrectIdTest() {
        Optional<Genre> expected = genreRepository.getById(INCORRECT_GENRE_ID);
        assertThat(expected).isEqualTo(Optional.empty());
    }

}