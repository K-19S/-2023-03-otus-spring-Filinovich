package ru.otus.filinovich.dao.book_comments;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.filinovich.domain.BookComment;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookCommentRepositoryJpa implements BookCommentRepository {

    private final EntityManager em;

    @Override
    public List<BookComment> getByBookId(Long id) {
        var query = em.createQuery("select c from BookComment c where c.book.id = :id", BookComment.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public Optional<BookComment> getById(Long id) {
        return Optional.ofNullable(em.find(BookComment.class, id));
    }

    @Override
    public BookComment create(BookComment bookComment) {
        em.persist(bookComment);
        return bookComment;
    }

    @Override
    public BookComment update(BookComment bookComment) {
        return em.merge(bookComment);
    }

    @Override
    public void deleteById(Long id) {
        var query = em.createQuery("delete from BookComment where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
