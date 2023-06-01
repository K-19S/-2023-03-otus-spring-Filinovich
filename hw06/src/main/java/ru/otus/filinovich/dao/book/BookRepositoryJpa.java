package ru.otus.filinovich.dao.book;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.filinovich.domain.Book;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
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
        Query query = em.createQuery("delete from Book where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
