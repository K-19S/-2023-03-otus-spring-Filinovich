package ru.otus.filinovich.service.author.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.filinovich.dao.author.AuthorRepository;
import ru.otus.filinovich.domain.Author;
import ru.otus.filinovich.service.user.interaction.UserInteractionService;
import ru.otus.filinovich.util.MessageProvider;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Component
@ExtendWith(SpringExtension.class)
@Import(AuthorServiceImpl.class)
class AuthorServiceImplTest {

    private static List<Author> testAuthors;

    @Autowired
    private AuthorServiceImpl authorService;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private UserInteractionService userInteractionService;

    @MockBean
    private MessageProvider messageProvider;

    @BeforeAll
    public static void initAuthors() {
        testAuthors = new ArrayList<>(2);
        testAuthors.add(new Author("Agatha Christie"));
        testAuthors.add(new Author("Stephen King"));
    }

    @Test
    public void updateAuthorNameTest() {
        String newAuthorName = "NEW_AUTHOR_TEST_NAME";
        given(authorRepository.findAll()).willReturn(testAuthors);
        given(userInteractionService.chooseAuthorFromList(testAuthors)).willReturn(testAuthors.get(0));
        given(userInteractionService.getTextWithPrompt(any())).willReturn(newAuthorName);

        authorService.updateAuthor();

        testAuthors.get(0).setName(newAuthorName);

        verify(authorRepository, times(1)).findAll();
        verify(userInteractionService, times(1)).chooseAuthorFromList(testAuthors);
        verify(userInteractionService, times(1)).getTextWithPrompt(any());
        verify(authorRepository, times(1)).save(testAuthors.get(0));
    }

}