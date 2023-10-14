package ru.otus.filinovich.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.filinovich.domain.Genre;
import ru.otus.filinovich.dto.GenreDto;
import ru.otus.filinovich.service.genre.GenreService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest
@ContextConfiguration(classes = GenreController.class)
class GenreControllerTest {

    private static final List<Genre> TEST_GENRES = new ArrayList<>();

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GenreService genreService;

    @BeforeAll
    public static void initTestGenres() {
        Genre genre1 = new Genre("Genre1");
        genre1.setId("64c50941ddce6b0b5c91b711");
        TEST_GENRES.add(genre1);

        Genre genre2 = new Genre("Genre2");
        genre2.setId("64c50941ddce6b0b5c91b712");
        TEST_GENRES.add(genre2);
    }

    @Test
    void getAllGenresTest() throws Exception {
        given(genreService.getAllGenres()).willReturn(TEST_GENRES);

        String json = objectMapper.writeValueAsString(TEST_GENRES.stream()
                .map(GenreDto::toDto)
                .collect(Collectors.toList()));
        mvc.perform(get("/genres"))
                .andExpect(status().isOk())
                .andExpect(content().json(json));

        verify(genreService, times(1)).getAllGenres();
    }
}