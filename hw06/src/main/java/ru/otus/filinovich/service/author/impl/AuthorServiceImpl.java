package ru.otus.filinovich.service.author.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.filinovich.dao.author.AuthorRepository;
import ru.otus.filinovich.domain.Author;
import ru.otus.filinovich.service.author.AuthorService;
import ru.otus.filinovich.service.user.interaction.UserInteractionService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    private final UserInteractionService userInteractionService;

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.getAll();
    }

    @Override
    public Author getAuthorById(Long id) {
        return authorRepository.getById(id).orElseThrow();
    }

    @Override
    public Author getAuthorByIdWithPromptAll() {
        Author chosenAuthor;
        do {
            Long authorId = userInteractionService.chooseAuthorIdFromList(getAllAuthors());
            chosenAuthor = getAuthorById(authorId);
        } while (chosenAuthor == null);
        return chosenAuthor;
    }
}
