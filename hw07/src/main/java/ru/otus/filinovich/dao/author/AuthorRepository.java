package ru.otus.filinovich.dao.author;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.filinovich.domain.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

}
