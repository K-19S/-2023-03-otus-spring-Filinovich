package ru.otus.filinovich.dao.book;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.filinovich.domain.Book;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations namedParameterJdbc;


    @Override
    public List<Book> getAll() {
        String query = "select b.*, a.*, g.* " +
                "from books b " +
                "left join authors a on b.author_id = a.id " +
                "left join genres g on b.genre_id = g.id";
        return namedParameterJdbc.query(query, new BookMapper());
    }

    @Override
    public Book getById(Long id) {
        String query = "select b.*, a.*, g.* " +
                "from books b " +
                "left join authors a on b.author_id = a.id " +
                "left join genres g on b.genre_id = g.id " +
                "where b.id = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        try {
            return namedParameterJdbc.queryForObject(query, parameterSource, new BookMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public boolean existById(Long id) {
        String query = "select count(*) from books where id = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        Integer count = namedParameterJdbc.queryForObject(query, parameterSource, Integer.class);
        return count != null && count > 0;
    }

    @Override
    public Long create(Book book) {
        String query = "insert into books (name, author_id, genre_id) values (:name, :author.id, :genre.id)";
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(book);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbc.update(query, parameterSource, keyHolder);
        return (Long) keyHolder.getKey();
    }

    @Override
    public void update(Book book) {
        String query = "update books set name = :name, author_id = :author.id, genre_id = :genre.id where id = :id";
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(book);
        namedParameterJdbc.update(query, parameterSource);
    }

    @Override
    public void deleteById(Long id) {
        String query = "delete from books where id = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        namedParameterJdbc.update(query, parameterSource);
    }
}
