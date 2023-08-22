package ru.otus.filinovich.converter;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.filinovich.domain.jpa.JpaBook;
import ru.otus.filinovich.domain.mongo.MongoAuthor;
import ru.otus.filinovich.domain.mongo.MongoBook;
import ru.otus.filinovich.domain.mongo.MongoGenre;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class BookJpaToMongoConverter implements Converter<JpaBook, MongoBook> {

    private final GenreJpaToMongoConverter genreConverter;

    private final AuthorJpaToMongoConverter authorConverter;

    private final Map<Long, MongoBook> cache = new HashMap<>();

    @Override
    public MongoBook convert(JpaBook source) {
        if (cache.containsKey(source.getId())) {
            return cache.get(source.getId());
        }
        MongoBook book = new MongoBook();
        book.setName(source.getName());
        MongoGenre genre = genreConverter.convert(source.getGenre());
        book.setGenre(genre);
        List<MongoAuthor> authors = source.getAuthors().stream().map(authorConverter::convert).toList();
        book.setAuthors(authors);
        book.setId(new ObjectId());
        cache.put(source.getId(), book);
        return book;
    }
}
