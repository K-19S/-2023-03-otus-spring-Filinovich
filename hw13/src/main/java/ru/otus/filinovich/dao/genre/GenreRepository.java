package ru.otus.filinovich.dao.genre;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.filinovich.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

}
