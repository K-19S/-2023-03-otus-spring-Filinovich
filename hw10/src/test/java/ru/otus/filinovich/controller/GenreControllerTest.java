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
import ru.otus.filinovich.domain.Genre;
import ru.otus.filinovich.dto.GenreDto;
import ru.otus.filinovich.service.genre.GenreService;

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

        String json = objectMapper.writeValueAsString(TEST_GENRES.stream().map(GenreDto::toDto).toList());
        mvc.perform(get("/genres"))
                .andExpect(status().isOk())
                .andExpect(content().json(json));

        verify(genreService, times(1)).getAllGenres();
    }

    @Test
    void createNewGenreTest() throws Exception {
        Genre testGenre = TEST_GENRES.get(0);
        String json = objectMapper.writeValueAsString(GenreDto.toDto(testGenre));
        mvc.perform(post("/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        verify(genreService, times(1)).save(testGenre);
    }

    @Test
    void updateGenreTest() throws Exception {
        Genre testGenre = TEST_GENRES.get(0);
        given(genreService.update(testGenre)).willReturn(true);
        String json = objectMapper.writeValueAsString(GenreDto.toDto(testGenre));
        mvc.perform(put("/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(genreService, times(1)).update(testGenre);
    }

    @Test
    void updateGenreFailedTest() throws Exception {
        Genre testGenre = new Genre(TEST_GENRES.get(0).getName());
        given(genreService.update(testGenre)).willReturn(false);
        String json = objectMapper.writeValueAsString(GenreDto.toDto(testGenre));
        mvc.perform(put("/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());

        verify(genreService, times(1)).update(testGenre);
    }

    @Test
    void deleteGenreTest() throws Exception {
        String id = TEST_GENRES.get(0).getId();
        mvc.perform(delete("/genres").param("id", id))
                .andExpect(status().isNoContent());

        verify(genreService, times(1)).deleteById(id);
    }
}