package ru.otus.filinovich.dao.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.otus.filinovich.domain.Genre;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations namedParameterJdbc;

    @Override
    public List<Genre> getAll() {
        String query = "select * from genres";
        return namedParameterJdbc.query(query, new GenreMapper());
    }

    @Override
    public Genre getById(Long id) {
        String query = "select * from genres where id = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        try {
            return namedParameterJdbc.queryForObject(query, parameterSource, new GenreMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
