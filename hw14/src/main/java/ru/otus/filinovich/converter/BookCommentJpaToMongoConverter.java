package ru.otus.filinovich.converter;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.filinovich.domain.jpa.JpaBookComment;
import ru.otus.filinovich.domain.mongo.MongoBook;
import ru.otus.filinovich.domain.mongo.MongoBookComment;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class BookCommentJpaToMongoConverter implements Converter<JpaBookComment, MongoBookComment> {

    private final BookJpaToMongoConverter bookConverter;

    private final Map<Long, MongoBookComment> cache = new HashMap<>();

    @Override
    public MongoBookComment convert(JpaBookComment source) {
        if (cache.containsKey(source.getId())) {
            return cache.get(source.getId());
        }
        MongoBookComment comment = new MongoBookComment();
        comment.setText(source.getText());
        MongoBook book = bookConverter.convert(source.getBook());
        comment.setBook(book);
        comment.setId(new ObjectId());
        cache.put(source.getId(), comment);
        return comment;
    }
}
