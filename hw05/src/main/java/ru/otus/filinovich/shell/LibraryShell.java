package ru.otus.filinovich.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.filinovich.domain.Book;
import ru.otus.filinovich.service.book.BookService;
import ru.otus.filinovich.util.MessageProvider;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class LibraryShell {

    private final BookService bookService;

    private final MessageProvider messageProvider;

    @ShellMethod(value = "Show all books in the library", key = {"books", "all"})
    public String showAllBooks() {
        List<Book> books = bookService.getAllBooks();
        StringBuilder builder = new StringBuilder();
        books.forEach(book -> {
            String bookDescription = bookService.getDescriptionOfBookWithId(book);
            builder.append(bookDescription).append("\n");
        });
        return builder.toString();
    }

    @ShellMethod(value = "Show book with specific id", key = "book")
    public String showBookById(@ShellOption Long id) {
        Book book = bookService.getBookById(id);
        return bookService.getDescriptionOfBook(book);
    }

    @ShellMethod(value = "Create new book", key = {"create", "create-book"})
    public String create() {
        Book newBook = bookService.createNewBook();
        return bookService.getDescriptionOfBook(newBook);
    }

    @ShellMethod(value = "Update exist book", key = {"update", "update-book"})
    public String update() {
        String prompt = messageProvider.getMessage("user_input.book");
        Book updatedBook = bookService.getBookByIdWithPromptAll(prompt);
        bookService.updateBook(updatedBook);
        return bookService.getDescriptionOfBook(updatedBook);
    }

    @ShellMethod(value = "Delete book", key = {"delete", "delete-book"})
    public String delete() {
        String prompt = messageProvider.getMessage("user_input.book_for_deleting");
        Book book = bookService.getBookByIdWithPromptAll(prompt);
        if (bookService.deleteById(book.getId())) {
            return messageProvider.getMessage("successful_book_deleting");
        } else {
            return messageProvider.getMessage("failed_book_deleting");
        }
    }
}
