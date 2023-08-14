package ru.otus.filinovich.service.book;

import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.otus.filinovich.domain.Book;

import java.util.List;

public interface BookService {

    @PostFilter("hasPermission(filterObject, 'READ')")
    List<Book> getAllBooks();

    @PostAuthorize("hasPermission(returnObject, 'READ')")
    Book getBookById(Long id);

    @PreAuthorize("#book.id!=null ? hasPermission(#book, 'WRITE') : hasRole('ADMIN')")
    void save(@Param("book") Book book);

    @PreAuthorize("hasRole('ADMIN')")
    void deleteById(Long id);
}
