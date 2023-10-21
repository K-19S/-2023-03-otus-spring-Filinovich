package ru.otus.filinovich.resilince.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.filinovich.resilince.clients.BookCommentsClient;
import ru.otus.filinovich.resilince.dto.BookCommentDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookCommentsController {

    private final BookCommentsClient client;

    @GetMapping("/comments")
    public ResponseEntity<List> getCommentsByBookId(@RequestParam String bookId,
                                                    @RequestHeader("Authorization") String authorization) {
        return client.getCommentsByBookId(bookId, authorization);
    }

    @PostMapping("/comments")
    public ResponseEntity<?> addComment(@RequestBody BookCommentDto commentDto,
                                        @RequestHeader("Authorization") String authorization) {
        return client.addComment(commentDto, authorization);
    }
}
