package ru.otus.filinovich.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.filinovich.domain.Book;
import ru.otus.filinovich.domain.BookComment;
import ru.otus.filinovich.dto.BookCommentDto;
import ru.otus.filinovich.service.book_comment.BookCommentService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest
@ContextConfiguration(classes = BookCommentController.class)
class BookCommentControllerTest {

    private static final List<BookComment> TEST_COMMENTS = new ArrayList<>();

    private static final String TEST_BOOK_ID = "64c50941ddce6b0b5c91b711";

    private static final Book TEST_BOOK = new Book();

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookCommentService bookCommentService;

    @BeforeAll
    public static void initComments() {
        TEST_BOOK.setId(TEST_BOOK_ID);
        TEST_BOOK.setName("BookName");
        TEST_COMMENTS.add(new BookComment("64c637f8055ba60bd79dbf92", "Comment1", TEST_BOOK));
        TEST_COMMENTS.add(new BookComment("64c637f8055ba60bd79dbf93", "Comment2", TEST_BOOK));
    }

    @Test
    void getCommentsByBookId() throws Exception {
        given(bookCommentService.getAllCommentsByBookId(TEST_BOOK_ID)).willReturn(TEST_COMMENTS);
        String json = objectMapper.writeValueAsString(TEST_COMMENTS.stream().map(BookCommentDto::toDto).toList());
        mvc.perform(get("/comments").param("bookId", TEST_BOOK_ID))
                .andExpect(status().isOk())
                .andExpect(content().json(json));

        verify(bookCommentService, times(1)).getAllCommentsByBookId(TEST_BOOK_ID);
    }

    @Test
    void addComment() throws Exception {
        BookComment comment = new BookComment();
        comment.setId(TEST_COMMENTS.get(0).getId());
        comment.setText(TEST_COMMENTS.get(0).getText());
        String json = objectMapper.writeValueAsString(BookCommentDto.toDto(TEST_COMMENTS.get(0)));
        mvc.perform(post("/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        verify(bookCommentService, times(1)).createComment(comment, TEST_BOOK_ID);
    }
}