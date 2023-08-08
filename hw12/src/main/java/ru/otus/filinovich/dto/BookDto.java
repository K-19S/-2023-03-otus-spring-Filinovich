package ru.otus.filinovich.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.filinovich.domain.Author;
import ru.otus.filinovich.domain.Book;
import ru.otus.filinovich.domain.Genre;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private String id;

    @NotBlank
    @Size(min = 2, max = 50)
    private String name;

    @NotNull
    private Genre genre;

    @NotEmpty
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
        if (!bookDto.getId().isBlank()) {
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
