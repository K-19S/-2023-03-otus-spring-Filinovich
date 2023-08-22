package ru.otus.filinovich.converter;

import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.filinovich.domain.jpa.JpaAuthor;
import ru.otus.filinovich.domain.mongo.MongoAuthor;

import java.util.HashMap;
import java.util.Map;

@Component
public class AuthorJpaToMongoConverter implements Converter<JpaAuthor, MongoAuthor> {

    private final Map<Long, MongoAuthor> cache = new HashMap<>();

    @Override
    public MongoAuthor convert(JpaAuthor source) {
        if (cache.containsKey(source.getId())) {
            return cache.get(source.getId());
        }
        MongoAuthor author = new MongoAuthor();
        author.setName(source.getName());
        author.setId(new ObjectId());
        cache.put(source.getId(), author);
        return author;
    }
}
