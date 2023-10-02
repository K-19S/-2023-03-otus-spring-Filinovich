package ru.otus.filinovich.resilince.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.filinovich.resilince.model.Genre;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenreDto {
    private String id;

    private String name;

    public static GenreDto toDto(Genre genre) {
        GenreDto dto = new GenreDto();
        dto.setId(genre.getId());
        dto.setName(genre.getName());
        return dto;
    }

    public static Genre fromDto(GenreDto dto) {
        Genre genre = new Genre();
        genre.setId(dto.getId());
        genre.setName(dto.getName());
        return genre;
    }
}
