package ru.otus.filinovich.dao.author;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.filinovich.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
