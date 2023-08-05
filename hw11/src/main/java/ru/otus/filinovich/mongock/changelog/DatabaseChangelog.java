package ru.otus.filinovich.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import ru.otus.filinovich.domain.Author;
import ru.otus.filinovich.domain.Book;
import ru.otus.filinovich.domain.BookComment;
import ru.otus.filinovich.domain.Genre;

import java.util.ArrayList;
import java.util.List;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropCollections", author = "s_filinovich", runAlways = true)
    public void dropDb(MongockTemplate mongockTemplate) {
        mongockTemplate.dropCollection(BookComment.class);
        mongockTemplate.dropCollection(Book.class);
        mongockTemplate.dropCollection(Genre.class);
        mongockTemplate.dropCollection(Author.class);
    }

    @ChangeSet(order = "002", id = "insertGenres", author = "s_filinovich", runAlways = true)
    public void insertData(MongockTemplate mongockTemplate) {
        List<Genre> genres = getGenres();
        List<Author> authors = getAuthors();
        mongockTemplate.insertAll(genres);
        mongockTemplate.insertAll(authors);
        List<Book> books = getBooks(genres, authors);
        mongockTemplate.insertAll(books);
        mongockTemplate.insertAll(getComments(books));
    }

    private List<Genre> getGenres() {
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre("Mystery"));
        genres.add(new Genre("Thriller"));
        genres.add(new Genre("Horror"));
        genres.add(new Genre("Historical"));
        genres.add(new Genre("Western"));
        genres.add(new Genre("Fantasy"));
        return genres;
    }

    private List<Author> getAuthors() {
        List<Author> authors = new ArrayList<>();
        authors.add(new Author("Agatha Christie"));
        authors.add(new Author("James Patterson"));
        authors.add(new Author("Stephen King"));
        authors.add(new Author("Howard Lovecraft"));
        authors.add(new Author("Diana Gabaldon"));
        authors.add(new Author("Louis Lamour"));
        authors.add(new Author("George R. R. Martin"));
        authors.add(new Author("Joanne Rowling"));
        return authors;
    }

    private List<Book> getBooks(List<Genre> genres, List<Author> authors) {
        List<Book> books = new ArrayList<>();
        books.add(new Book("Ten Little Niggers",
                List.of(authors.get(0)), genres.get(0)));
        books.add(new Book("The Murder House",
                List.of(authors.get(1)), genres.get(1)));
        books.add(new Book("IT",
                List.of(authors.get(2)), genres.get(2)));
        books.add(new Book("At the Mountains of Madness",
                List.of(authors.get(3)), genres.get(2)));
        books.add(new Book("Lord John and the Private Matter",
                List.of(authors.get(4)), genres.get(3)));
        books.add(new Book("Sackett",
                List.of(authors.get(5)), genres.get(4)));
        books.add(new Book("A Game of Thrones",
                List.of(authors.get(6)), genres.get(5)));
        books.add(new Book("Harry Potter and the Deathly Hallows",
                List.of(authors.get(7)), genres.get(5)));
        return books;
    }

    private List<BookComment> getComments(List<Book> books) {
        List<BookComment> comments = new ArrayList<>();
        comments.add(new BookComment("Comment 1", books.get(0)));
        comments.add(new BookComment("Comment 2", books.get(1)));
        comments.add(new BookComment("Comment 3", books.get(2)));
        comments.add(new BookComment("Comment 4", books.get(3)));
        comments.add(new BookComment("Comment 5", books.get(4)));
        comments.add(new BookComment("Comment 6", books.get(5)));
        comments.add(new BookComment("Comment 7", books.get(6)));
        comments.add(new BookComment("Comment 8", books.get(7)));
        comments.add(new BookComment("Comment 9", books.get(7)));
        comments.add(new BookComment("Comment 10", books.get(7)));
        return comments;
    }
}



//package ru.otus.filinovich.mongock.changelog;
//
//import com.mongodb.client.result.InsertManyResult;
//import com.mongodb.reactivestreams.client.ClientSession;
//import com.mongodb.reactivestreams.client.MongoDatabase;
//import io.mongock.api.annotations.ChangeUnit;
//import io.mongock.api.annotations.Execution;
//import io.mongock.driver.mongodb.reactive.util.MongoSubscriberSync;
//import io.mongock.driver.mongodb.reactive.util.SubscriberSync;
//import ru.otus.filinovich.domain.Author;
//import ru.otus.filinovich.domain.Book;
//import ru.otus.filinovich.domain.BookComment;
//import ru.otus.filinovich.domain.Genre;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@ChangeUnit(id = "initializer", order = "1", author = "s_filinovich")
//public class DatabaseChangelog {
//
//    private List<Genre> genres;
//
//    private List<Author> authors;
//
//    private List<Book> books;
//
//    @Execution
//    public void execution(ClientSession clientSession, MongoDatabase mongoDatabase) {
//        SubscriberSync<InsertManyResult> subscriber = new MongoSubscriberSync<>();
//        mongoDatabase.getCollection("genres", Genre.class)
//                .insertMany(clientSession, getGenres())
//                .subscribe(subscriber);
//        mongoDatabase.getCollection("authors", Author.class)
//                .insertMany(clientSession, getAuthors())
//                .subscribe(subscriber);
//        mongoDatabase.getCollection("books", Book.class)
//                .insertMany(clientSession, getBooks())
//                .subscribe(subscriber);
//        mongoDatabase.getCollection("bookComments", BookComment.class)
//                .insertMany(clientSession, getBookComments())
//                .subscribe(subscriber);
//        InsertManyResult result = subscriber.getFirst();
//    }
//
////    @Execution
////    public void dropDb(ClientSession clientSession, MongoDatabase mongoDatabase) {
////        mongoTemplate.dropCollection(Genre.class).subscribe();
////        mongoTemplate.dropCollection(Author.class).subscribe();
////        mongoTemplate.dropCollection(Book.class).subscribe();
////        mongoTemplate.dropCollection(BookComment.class).subscribe();
////        mongoTemplate.insertAll(Mono.just(getGenres())).subscribe();
////        mongoTemplate.insertAll(Mono.just(getAuthors())).subscribe();
////        mongoTemplate.insertAll(Mono.just(getBooks())).subscribe();
////        mongoTemplate.insertAll(Mono.just(getBookComments())).subscribe();
////    }
//
//    public List<Genre> getGenres() {
//        genres = new ArrayList<>();
//        genres.add(new Genre("Mystery"));
//        genres.add(new Genre("Thriller"));
//        genres.add(new Genre("Horror"));
//        genres.add(new Genre("Historical"));
//        genres.add(new Genre("Western"));
//        genres.add(new Genre("Fantasy"));
//        return genres;
//    }
//
//    public List<Author> getAuthors() {
//        authors = new ArrayList<>();
//        authors.add(new Author("Agatha Christie"));
//        authors.add(new Author("James Patterson"));
//        authors.add(new Author("Stephen King"));
//        authors.add(new Author("Howard Lovecraft"));
//        authors.add(new Author("Diana Gabaldon"));
//        authors.add(new Author("Louis Lamour"));
//        authors.add(new Author("George R. R. Martin"));
//        authors.add(new Author("Joanne Rowling"));
//        return authors;
//    }
//
//    public List<Book> getBooks() {
//        books = new ArrayList<>();
//        books.add(new Book("Ten Little Niggers", List.of(authors.get(0)), genres.get(0)));
//        books.add(new Book("The Murder House", List.of(authors.get(1)), genres.get(1)));
//        books.add(new Book("IT", List.of(authors.get(2)), genres.get(2)));
//        books.add(new Book("At the Mountains of Madness", List.of(authors.get(3)), genres.get(2)));
//        books.add(new Book("Lord John and the Private Matter", List.of(authors.get(4)), genres.get(3)));
//        books.add(new Book("Sackett", List.of(authors.get(5)), genres.get(4)));
//        books.add(new Book("A Game of Thrones", List.of(authors.get(6)), genres.get(5)));
//        books.add(new Book("Harry Potter and the Deathly Hallows", List.of(authors.get(7)), genres.get(5)));
//        return books;
//    }
//
//    public List<BookComment> getBookComments() {
//        List<BookComment> comments = new ArrayList<>();
//        comments.add(new BookComment("Comment 1", books.get(0)));
//        comments.add(new BookComment("Comment 2", books.get(1)));
//        comments.add(new BookComment("Comment 3", books.get(2)));
//        comments.add(new BookComment("Comment 4", books.get(3)));
//        comments.add(new BookComment("Comment 5", books.get(4)));
//        comments.add(new BookComment("Comment 6", books.get(5)));
//        comments.add(new BookComment("Comment 7", books.get(6)));
//        comments.add(new BookComment("Comment 8", books.get(7)));
//        comments.add(new BookComment("Comment 9", books.get(7)));
//        comments.add(new BookComment("Comment 10", books.get(7)));
//        return comments;
//    }
//}
