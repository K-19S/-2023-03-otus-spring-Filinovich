package ru.otus.filinovich.domain.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "bookComments")
@EqualsAndHashCode
public class MongoBookComment {

    private ObjectId id;

    private String text;

    @DBRef
    private MongoBook book;
}
