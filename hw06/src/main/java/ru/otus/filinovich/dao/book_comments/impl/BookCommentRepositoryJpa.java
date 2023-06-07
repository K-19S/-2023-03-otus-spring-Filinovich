package ru.otus.filinovich.dao.book_comments.impl;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.filinovich.dao.book_comments.BookCommentRepository;
import ru.otus.filinovich.domain.BookComment;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookCommentRepositoryJpa implements BookCommentRepository {

    private final EntityManager em;

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
        getById(id).ifPresent(em::remove);
    }
}
