package ru.otus.filinovich.domain.mongo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "authors")
@EqualsAndHashCode
public class MongoAuthor {

    @Id
    private ObjectId id;

    private String name;
}


