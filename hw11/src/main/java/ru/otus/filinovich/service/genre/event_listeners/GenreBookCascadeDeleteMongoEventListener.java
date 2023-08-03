package ru.otus.filinovich.service.genre.event_listeners;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.filinovich.domain.Genre;
import ru.otus.filinovich.service.book.BookService;
import ru.otus.filinovich.service.genre.GenreService;

@Component
@RequiredArgsConstructor
public class GenreBookCascadeDeleteMongoEventListener extends AbstractMongoEventListener<Genre> {

    private final BookService bookService;

    private final GenreService genreService;

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Genre> event) {
        Document source = event.getSource();
        ObjectId genreId = source.getObjectId("_id");
        genreService.getById(genreId.toString())
            .map(bookService::getByGenre)
            .subscribe(bookService::deleteAll);
    }
}