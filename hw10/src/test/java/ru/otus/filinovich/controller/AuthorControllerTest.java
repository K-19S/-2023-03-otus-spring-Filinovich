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
import ru.otus.filinovich.domain.Author;
import ru.otus.filinovich.dto.AuthorDto;
import ru.otus.filinovich.service.author.AuthorService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest
@ContextConfiguration(classes = AuthorController.class)
class AuthorControllerTest {

    private static final List<Author> TEST_AUTHORS = new ArrayList<>();

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthorService authorService;

    @BeforeAll
    public static void initTestAuthors() {
        Author author1 = new Author("Author1");
        author1.setId("64c50941ddce6b0b5c91b711");
        TEST_AUTHORS.add(author1);

        Author author2 = new Author("Author2");
        author2.setId("64c50941ddce6b0b5c91b712");
        TEST_AUTHORS.add(author2);
    }

    @Test
    void getAllAuthorsTest() throws Exception {
        given(authorService.getAllAuthors()).willReturn(TEST_AUTHORS);
        String json = objectMapper.writeValueAsString(TEST_AUTHORS.stream().map(AuthorDto::toDto).toList());

        mvc.perform(get("/authors"))
                .andExpect(status().isOk())
                .andExpect(content().json(json));

        verify(authorService, times(1)).getAllAuthors();
    }

    @Test
    void createNewAuthorTest() throws Exception {
        Author testAuthor = TEST_AUTHORS.get(0);
        String json = objectMapper.writeValueAsString(AuthorDto.toDto(testAuthor));
        mvc.perform(post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());

        verify(authorService, times(1)).save(testAuthor);
    }

    @Test
    void updateAuthorTest() throws Exception {
        Author testAuthor = TEST_AUTHORS.get(0);
        given(authorService.update(testAuthor)).willReturn(true);
        String json = objectMapper.writeValueAsString(AuthorDto.toDto(testAuthor));
        mvc.perform(put("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());

        verify(authorService, times(1)).update(testAuthor);
    }

    @Test
    void updateAuthorFailedTest() throws Exception {
        Author testAuthor = new Author(TEST_AUTHORS.get(0).getName());
        given(authorService.update(testAuthor)).willReturn(false);
        String json = objectMapper.writeValueAsString(AuthorDto.toDto(testAuthor));
        mvc.perform(put("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());

        verify(authorService, times(1)).update(testAuthor);
    }

    @Test
    void deleteAuthorTest() throws Exception {
        String id = TEST_AUTHORS.get(0).getId();
        mvc.perform(delete("/authors").param("id", id))
                .andExpect(status().isNoContent());

        verify(authorService, times(1)).deleteById(id);
    }
}