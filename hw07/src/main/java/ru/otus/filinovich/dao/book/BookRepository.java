package ru.otus.filinovich.dao.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.filinovich.domain.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
