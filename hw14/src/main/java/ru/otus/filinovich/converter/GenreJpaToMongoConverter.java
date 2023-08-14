package ru.otus.filinovich.converter;

import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.filinovich.domain.jpa.JpaGenre;
import ru.otus.filinovich.domain.mongo.MongoGenre;

import java.util.HashMap;
import java.util.Map;

@Component
public class GenreJpaToMongoConverter implements Converter<JpaGenre, MongoGenre> {

    private final Map<Long, MongoGenre> cache = new HashMap<>();

    @Override
    public MongoGenre convert(JpaGenre source) {
        if (cache.containsKey(source.getId())) {
            return cache.get(source.getId());
        }
        MongoGenre genre = new MongoGenre();
        genre.setName(source.getName());
        genre.setId(new ObjectId());
        cache.put(source.getId(), genre);
        return genre;
    }
}
