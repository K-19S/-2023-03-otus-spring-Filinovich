package ru.otus.filinovich.service.book.event_listeners;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import ru.otus.filinovich.domain.Book;
import ru.otus.filinovich.service.book_comment.BookCommentService;

@Component
@RequiredArgsConstructor
public class BookCommentCascadeDeleteMongoEventListener extends AbstractMongoEventListener<Book> {

    private final BookCommentService bookCommentService;

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Book> event) {
        Document source = event.getSource();
        ObjectId bookId = source.getObjectId("_id");
        bookCommentService.findByBookId(bookId.toString())
            .flatMap(comment -> bookCommentService.delete(Mono.just(comment)))
            .subscribe();
    }
}
