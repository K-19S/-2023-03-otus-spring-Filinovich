package ru.otus.filinovich.resilince.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.filinovich.resilince.model.Author;
import ru.otus.filinovich.resilince.model.Book;
import ru.otus.filinovich.resilince.model.Genre;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private String id;

    private String name;

    private Genre genre;

    private String fileName;

    private String fileBytes;

    private List<Author> authors = new ArrayList<>();

    public static BookDto toDto(Book book) {
        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setName(book.getName());
        dto.setGenre(book.getGenre());
        dto.setAuthors(book.getAuthors());
        return dto;
    }

    public static Book fromDto(BookDto bookDto) {
        Book book = new Book();
        if (bookDto.getId() == null || !bookDto.getId().isBlank()) {
            book.setId(bookDto.getId());
        } else {
            book.setId(null);
        }
        book.setName(bookDto.getName());
        book.setGenre(bookDto.getGenre());
        book.setAuthors(bookDto.getAuthors());
        return book;
    }
}
