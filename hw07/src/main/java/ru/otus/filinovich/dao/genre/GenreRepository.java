package ru.otus.filinovich.dao.genre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.filinovich.domain.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

}
