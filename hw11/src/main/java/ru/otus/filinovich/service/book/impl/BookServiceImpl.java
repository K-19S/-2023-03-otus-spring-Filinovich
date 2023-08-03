package ru.otus.filinovich.service.book.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.filinovich.dao.book.BookRepository;
import ru.otus.filinovich.domain.Author;
import ru.otus.filinovich.domain.Book;
import ru.otus.filinovich.domain.Genre;
import ru.otus.filinovich.service.book.BookService;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public Flux<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Flux<Book> getByGenre(Genre genre) {
        return bookRepository.findByGenre(genre);
    }

    @Override
    public Flux<Book> getByAuthor(Author author) {
        return bookRepository.findByAuthorsContaining(author);
    }

    @Override
    public Mono<Book> getBookById(String id) {
        return bookRepository.findById(id);
    }

    @Override
    public Mono<Book> save(Mono<Book> book) {
        return book.flatMap(bookRepository::save);
    }

    @Override
    public Mono<Book> update(Mono<Book> book) {
        return book.filter(var -> var.getId() != null && !var.getId().isBlank())
                .flatMap(bookRepository::save);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return bookRepository.deleteById(id);
    }

    @Override
    public void deleteAll(Flux<Book> books) {
        bookRepository.deleteAll(books).subscribe();
    }
}
