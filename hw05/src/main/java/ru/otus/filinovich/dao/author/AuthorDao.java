package ru.otus.filinovich.dao.author;

import ru.otus.filinovich.domain.Author;

import java.util.List;

public interface AuthorDao {

    List<Author> getAll();

    Author getById(Long id);
}
