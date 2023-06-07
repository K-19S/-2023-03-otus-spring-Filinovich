package ru.otus.filinovich.dao.author;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.filinovich.dao.author.impl.AuthorRepositoryJpa;
import ru.otus.filinovich.domain.Author;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(AuthorRepositoryJpa.class)
class AuthorRepositoryJpaTest {

    private static final Long CORRECT_AUTHOR_ID = 1L;

    private static final Long INCORRECT_AUTHOR_ID = -100L;

    private static final Integer CORRECT_AUTHORS_SIZE = 8;

    @Autowired
    private AuthorRepositoryJpa authorRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    public void getAllTest() {
        List<Author> authors = authorRepository.getAll();
        assertThat(authors).isNotNull().hasSize(CORRECT_AUTHORS_SIZE)
                .allMatch(author -> !author.getName().isBlank());
    }

    @Test
    public void getByCorrectIdTest() {
        var expectedId = CORRECT_AUTHOR_ID;
        Optional<Author> expected = authorRepository.getById(expectedId);
        Author actual = em.find(Author.class, expectedId);
        assertThat(expected).isNotNull().get().isEqualTo(actual);
    }

    @Test
    public void getByIncorrectIdTest() {
        Optional<Author> expected = authorRepository.getById(INCORRECT_AUTHOR_ID);
        assertThat(expected).isEqualTo(Optional.empty());
    }

}