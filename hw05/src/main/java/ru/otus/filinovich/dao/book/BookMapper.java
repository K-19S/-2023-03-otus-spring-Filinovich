package ru.otus.filinovich.dao.book;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.filinovich.domain.Author;
import ru.otus.filinovich.domain.Book;
import ru.otus.filinovich.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        Author author = new Author();
        Genre genre = new Genre();
        book.setId(rs.getLong("id"));
        book.setName(rs.getString("name"));
        author.setId(rs.getLong("author_id"));
        author.setName(rs.getString("authors.name"));
        genre.setId(rs.getLong("genre_id"));
        genre.setName(rs.getString("genres.name"));
        book.setAuthor(author);
        book.setGenre(genre);
        return book;
    }
}
