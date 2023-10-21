package ru.otus.filinovich.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.filinovich.domain.Rating;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookWithRatingDto {

    private BookDto book;

    private Rating rating;
}
