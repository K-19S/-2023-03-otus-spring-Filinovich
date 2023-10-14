package ru.otus.filinovich.service.author.event_listener;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.filinovich.domain.Author;
import ru.otus.filinovich.domain.Book;
import ru.otus.filinovich.service.author.AuthorService;
import ru.otus.filinovich.service.book.BookService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthorBookCascadeDeleteMongoEventListener extends AbstractMongoEventListener<Author> {

    private final BookService bookService;

    private final AuthorService authorService;

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Author> event) {
        Document source = event.getSource();
        ObjectId authorId = source.getObjectId("_id");
        Author author = authorService.getById(authorId.toString());
        List<Book> books = bookService.getByAuthor(author);
        bookService.deleteAll(books);
    }
}
