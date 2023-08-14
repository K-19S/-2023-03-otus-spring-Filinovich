package ru.otus.filinovich.domain.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "books")
@EqualsAndHashCode
public class MongoBook {

    @Id
    private ObjectId id;

    private String name;

    @DBRef
    private MongoGenre genre;

    @DBRef
    private List<MongoAuthor> authors = new ArrayList<>();
}

