package ru.otus.filinovich.dao.book.impl;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.filinovich.dao.book.BookRepository;
import ru.otus.filinovich.domain.Book;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Book> getAll() {
        EntityGraph<?> graph = em.getEntityGraph("books_genres_graph");
        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        query.setHint("jakarta.persistence.fetchgraph", graph);
        return query.getResultList();
    }

    @Override
    public Optional<Book> getById(Long id) {
        Map<String, Object> hints = new HashMap<>();
        EntityGraph<?> graph = em.getEntityGraph("books_genres_graph");
        hints.put("jakarta.persistence.fetchgraph", graph);
        return Optional.ofNullable(em.find(Book.class, id, hints));

    }

    @Override
    public boolean existById(Long id) {
        return em.find(Book.class, id) != null;
    }

    @Override
    public Book create(Book book) {
        em.persist(book);
        return book;
    }

    @Override
    public Book update(Book book) {
        return em.merge(book);
    }

    @Override
    public void deleteById(Long id) {
        getById(id).ifPresent(em::remove);
    }
}
