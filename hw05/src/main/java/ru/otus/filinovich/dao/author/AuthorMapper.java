package ru.otus.filinovich.dao.author;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.filinovich.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorMapper implements RowMapper<Author> {
    @Override
    public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
        Author author = new Author();
        author.setId(rs.getLong("id"));
        author.setName(rs.getString("name"));
        return author;
    }
}
