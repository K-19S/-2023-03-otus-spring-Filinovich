package ru.otus.filinovich.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.filinovich.domain.BookComment;
import ru.otus.filinovich.service.book_comment.BookCommentService;

@Controller
@RequiredArgsConstructor
public class BookCommentController {

    private final BookCommentService bookCommentService;

    @PostMapping("/comment")
    public String addComment(@RequestParam("bookId") String bookId,
                             @ModelAttribute("comment") BookComment comment) {
        bookCommentService.createComment(comment, bookId);
        return "redirect:/books";
    }
}
