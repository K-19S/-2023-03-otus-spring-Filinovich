package ru.otus.filinovich.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.filinovich.domain.Author;
import ru.otus.filinovich.domain.Book;
import ru.otus.filinovich.domain.BookComment;
import ru.otus.filinovich.domain.Genre;
import ru.otus.filinovich.dto.BookDto;
import ru.otus.filinovich.service.author.AuthorService;
import ru.otus.filinovich.service.book.BookService;
import ru.otus.filinovich.service.book_comment.BookCommentService;
import ru.otus.filinovich.service.genre.GenreService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final BookCommentService bookCommentService;

    private final GenreService genreService;

    private final AuthorService authorService;

    @GetMapping("/books")
    public String allBooks(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/book")
    public String editBook(@RequestParam("id") String id, Model model) {
        Book book = bookService.getBookById(id);
        BookDto bookDto = BookDto.toDto(book);
        model.addAttribute("book", bookDto);
        List<BookComment> comments = bookCommentService.getAllCommentsByBookId(id);
        model.addAttribute("comments", comments);
        fillTheEditBookFormWithDefaultData(model);
        return "editBook";
    }

    @GetMapping("/newBook")
    public String newBook(Model model) {
        model.addAttribute("book", new BookDto());
        model.addAttribute("comments", new ArrayList<>());
        fillTheEditBookFormWithDefaultData(model);
        return "editBook";
    }

    @GetMapping("/deleteBook")
    public String deleteBook(@RequestParam("id") String id, Model model) {
        bookService.deleteById(id);
        return allBooks(model);
    }

    @PostMapping("/book")
    public String saveBook(@Valid @ModelAttribute("book") BookDto bookDto,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            fillTheEditBookFormWithDefaultData(model);
            return "editBook";
        }
        Book book = BookDto.fromDto(bookDto);
        bookService.save(book);
        return "redirect:/books";
    }

    private void fillTheEditBookFormWithDefaultData(Model model) {
        List<Genre> genres = genreService.getAllGenres();
        model.addAttribute("genres", genres);
        List<Author> authors = authorService.getAllAuthors();
        model.addAttribute("authors", authors);
        model.addAttribute("comment", new BookComment());
    }
}
