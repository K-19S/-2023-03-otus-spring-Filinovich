package ru.otus.filinovich.service.book_comment.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.filinovich.dao.book_comments.BookCommentRepository;
import ru.otus.filinovich.domain.Book;
import ru.otus.filinovich.domain.BookComment;
import ru.otus.filinovich.service.book.BookService;
import ru.otus.filinovich.service.book_comment.BookCommentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookCommentServiceImpl implements BookCommentService {

    private final BookCommentRepository bookCommentRepository;

    private final BookService bookService;

    @Override
    public List<BookComment> getAllCommentsByBookId(Long id) {
        return bookCommentRepository.findByBookId(id);
    }

    @Override
    public void createComment(BookComment comment, Long bookId) {
        Book book = bookService.getBookById(bookId);
        comment.setBook(book);
        bookCommentRepository.save(comment);
    }

}
