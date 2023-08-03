package ru.otus.filinovich.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.filinovich.domain.Book;
import ru.otus.filinovich.domain.BookComment;
import ru.otus.filinovich.dto.BookCommentDto;
import ru.otus.filinovich.service.book_comment.BookCommentService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class BookCommentConfigTest {

    private static final List<BookComment> TEST_COMMENTS = new ArrayList<>();

    private static final String TEST_BOOK_ID = "64c50941ddce6b0b5c91b711";

    private static final Book TEST_BOOK = new Book();

    @Autowired
    private RouterFunction<ServerResponse> commentRouters;

    @MockBean
    private BookCommentService bookCommentService;

    @Autowired
    private ObjectMapper objectMapper;

    private WebTestClient client;

    @BeforeAll
    public static void initComments() {
        TEST_BOOK.setId(TEST_BOOK_ID);
        TEST_BOOK.setName("BookName");
        TEST_COMMENTS.add(new BookComment("64c637f8055ba60bd79dbf92", "Comment1", TEST_BOOK));
        TEST_COMMENTS.add(new BookComment("64c637f8055ba60bd79dbf93", "Comment2", TEST_BOOK));
    }

    @BeforeEach
    public void initWebTestClient() {
        client = WebTestClient
                .bindToRouterFunction(commentRouters)
                .build();
    }

    @Test
    void getCommentsByBookId() throws Exception {
        given(bookCommentService.getAllCommentsByBookId(TEST_BOOK_ID)).willReturn(Flux.fromIterable(TEST_COMMENTS));
        String json = objectMapper.writeValueAsString(TEST_COMMENTS.stream().map(BookCommentDto::toDto).toList());
        client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/comments")
                        .queryParam("bookId", TEST_BOOK_ID)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(json);

        verify(bookCommentService, times(1)).getAllCommentsByBookId(TEST_BOOK_ID);
    }

    @Test
    void addComment() throws Exception {
        BookComment comment = new BookComment();
        comment.setId(TEST_COMMENTS.get(0).getId());
        comment.setText(TEST_COMMENTS.get(0).getText());
        String json = objectMapper.writeValueAsString(BookCommentDto.toDto(TEST_COMMENTS.get(0)));
        given(bookCommentService.createComment(any(), eq(TEST_BOOK_ID))).willReturn(Mono.just(TEST_COMMENTS.get(0)));

        client.post()
                .uri("/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(json)
                .exchange()
                .expectStatus().isCreated();

        verify(bookCommentService, times(1)).createComment(any(), eq(TEST_BOOK_ID));
    }
}