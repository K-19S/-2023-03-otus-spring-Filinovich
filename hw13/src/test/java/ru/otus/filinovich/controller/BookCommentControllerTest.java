package ru.otus.filinovich.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.filinovich.domain.BookComment;
import ru.otus.filinovich.security.SecurityConfiguration;
import ru.otus.filinovich.service.book_comment.BookCommentService;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

// По другому никак не сработало, в том числе и
// @WebMvcTest(BookCommentController.class)
@WebMvcTest
@ContextConfiguration(classes = {
        BookCommentController.class,
        SecurityConfiguration.class})
class BookCommentControllerTest {

    private static final Long TEST_BOOK_ID = 100L;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookCommentService bookCommentService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void addCommentTest() throws Exception {
        BookComment bookComment = new BookComment();
        mvc.perform(post("/comment")
                .param("bookId", TEST_BOOK_ID.toString())
                .flashAttr("comment", bookComment))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/books"));

        verify(bookCommentService, times(1)).createComment(bookComment, TEST_BOOK_ID);
    }

    @Test
    public void addCommentSecurityTest() throws Exception {
        BookComment bookComment = new BookComment();
        mvc.perform(post("/comment")
                .param("bookId", TEST_BOOK_ID.toString())
                .flashAttr("comment", bookComment))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("location", "http://localhost/login"));
    }

}