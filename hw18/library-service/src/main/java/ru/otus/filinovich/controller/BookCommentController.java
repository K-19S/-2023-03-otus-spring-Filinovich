package ru.otus.filinovich.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.filinovich.dto.BookCommentDto;
import ru.otus.filinovich.service.book_comment.BookCommentService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class BookCommentController {

    private final BookCommentService bookCommentService;

    @GetMapping("/comments")
    public ResponseEntity<List<BookCommentDto>> getCommentsByBookId(@RequestParam String bookId) {
        List<BookCommentDto> comments = bookCommentService.getAllCommentsByBookId(bookId)
                .stream()
                .map(BookCommentDto::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/comments")
    public ResponseEntity<?> addComment(@RequestBody BookCommentDto commentDto) {
        bookCommentService.createComment(BookCommentDto.fromDto(commentDto), commentDto.getBookId());
        return ResponseEntity.status(201).build();
    }
}
