package ru.otus.filinovich.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import ru.otus.filinovich.domain.Genre;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenreDto {

    @Id
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
