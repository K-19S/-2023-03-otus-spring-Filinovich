package ru.otus.filinovich.resilince.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookFile {

    private String id;

    private Book book;

    private String name;

    private String bytes;
}
