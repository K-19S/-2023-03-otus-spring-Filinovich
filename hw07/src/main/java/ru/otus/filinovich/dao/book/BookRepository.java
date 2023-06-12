package ru.otus.filinovich.dao.book;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.filinovich.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
