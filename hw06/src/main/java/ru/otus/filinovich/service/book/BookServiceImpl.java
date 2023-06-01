package ru.otus.filinovich.service.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.filinovich.dao.book.BookRepository;
import ru.otus.filinovich.domain.Author;
import ru.otus.filinovich.domain.Book;
import ru.otus.filinovich.domain.Genre;
import ru.otus.filinovich.service.IOService;
import ru.otus.filinovich.service.author.AuthorService;
import ru.otus.filinovich.service.genre.GenreService;
import ru.otus.filinovich.util.MessageProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final AuthorService authorService;

    private final GenreService genreService;

    private final IOService ioService;

    private final BookRepository bookRepository;

    private final MessageProvider messageProvider;


    @Override
    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return bookRepository.getAll();
    }

    @Override
    @Transactional(readOnly = true)
    public String getAllBooksDescriptions() {
        List<Book> allBooks = getAllBooks();
        StringBuilder builder = new StringBuilder();
        allBooks.forEach(book -> {
            String bookDescription = getDescriptionOfBookWithId(book);
            builder.append(bookDescription).append("\n");
        });
        return builder.toString();
    }

    @Override
    @Transactional(readOnly = true)
    public Book getBookById(Long id) {
        return bookRepository.getById(id).orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public String getBookDescriptionById(Long id) {
        Book book = getBookById(id);
        return getDescriptionOfBook(book);
    }

    @Override
    @Transactional(readOnly = true)
    public Book getBookByIdWithPromptAll(String prompt) {
        getAllBooks().forEach(book -> {
            ioService.outputString(getDescriptionOfBookWithId(book));
        });

        Book book = null;
        do {
            Long choseBookId = ioService.readLongWithPrompt(prompt);
            book = getBookById(choseBookId);
        } while (book == null);
        return book;
    }

    @Override
    @Transactional
    public Book createNewBook() {
        String prompt = messageProvider.getMessage("user_input.book_name");
        String bookName = ioService.readStringWithPrompt(prompt);

        Genre genre = genreService.getGenreByIdWithPromptAll();
        Author author = authorService.getAuthorByIdWithPromptAll();

        Book book = new Book(bookName, List.of(author), genre);

        return bookRepository.create(book);
    }

    @Override
    public String getDescriptionOfBook(Book book) {
        String authorName = book.getAuthors().get(0) != null ? book.getAuthors().get(0).getName() :
                messageProvider.getMessage("unknown_author");
        String genreName = book.getGenre() != null ? book.getGenre().getName() :
                messageProvider.getMessage("unknown_genre");
        return "\"" + book.getName() + "\" " + authorName + ", " + genreName;
    }

    @Override
    public String getDescriptionOfBookWithId(Book book) {
        String description = getDescriptionOfBook(book);
        return description + " | ID: " + book.getId();
    }

    @Override
    @Transactional
    public void updateBook(Book updatedBook) {
        Map<Long, String> fieldsMap = getBookFieldMap();
        Map<String, BookFieldUpdater> updatersMap = getBookUpdatersMapFromFieldsFromBook(fieldsMap, updatedBook);
        fieldsMap.keySet().forEach(fieldNum ->
            ioService.outputString(fieldNum + ". " + fieldsMap.get(fieldNum))
        );
        String prompt = messageProvider.getMessage("user_input.edit_field");
        String fieldName = null;
        do {
            Long chosenFieldNumber = ioService.readLongWithPrompt(prompt);
            fieldName = fieldsMap.get(chosenFieldNumber);
        } while (fieldName == null);
        updatersMap.get(fieldName).update();
        bookRepository.update(updatedBook);
    }

    private Map<Long, String> getBookFieldMap() {
        Map<Long, String> fields = new HashMap<>();
        fields.put(1L, messageProvider.getMessage("book.name"));
        fields.put(2L, messageProvider.getMessage("book.author"));
        fields.put(3L, messageProvider.getMessage("book.genre"));
        return fields;
    }

    private Map<String, BookFieldUpdater> getBookUpdatersMapFromFieldsFromBook(
            Map<Long, String> fields, Book updatedBook) {
        Map<String, BookFieldUpdater> updatersMap = new HashMap<>();
        updatersMap.put(fields.get(1L), () -> {
            String prompt = messageProvider.getMessage("user_input.book_new_name");
            String newName = ioService.readStringWithPrompt(prompt);
            updatedBook.setName(newName);
        });
        updatersMap.put(fields.get(2L), () -> {
            Author newAuthor = authorService.getAuthorByIdWithPromptAll();
            updatedBook.setAuthors(List.of(newAuthor));
        });
        updatersMap.put(fields.get(3L), () -> {
            Genre newGenre = genreService.getGenreByIdWithPromptAll();
            updatedBook.setGenre(newGenre);
        });
        return updatersMap;
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        if (bookRepository.existById(id)) {
            bookRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
