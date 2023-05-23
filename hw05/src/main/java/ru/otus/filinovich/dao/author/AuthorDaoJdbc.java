package ru.otus.filinovich.dao.author;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.otus.filinovich.domain.Author;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations namedParameterJdbc;

    @Override
    public List<Author> getAll() {
        String query = "select * from authors";
        return namedParameterJdbc.query(query, new AuthorMapper());
    }

    @Override
    public Author getById(Long id) {
        String query = "select * from authors where id = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        try {
            return namedParameterJdbc.queryForObject(query, parameterSource, new AuthorMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
