package ru.otus.filinovich.service.author.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.filinovich.dao.author.AuthorRepository;
import ru.otus.filinovich.domain.Author;
import ru.otus.filinovich.service.author.AuthorService;
import ru.otus.filinovich.service.user.interaction.UserInteractionService;
import ru.otus.filinovich.util.MessageProvider;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    private final UserInteractionService userInteractionService;

    private final MessageProvider messageProvider;

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Author getAuthorByIdWithPromptAll() {
        Author chosenAuthor;
        do {
            chosenAuthor = userInteractionService.chooseAuthorFromList(getAllAuthors());
        } while (chosenAuthor == null);
        return chosenAuthor;
    }

    @Override
    public void updateAuthor() {
        Author author = getAuthorByIdWithPromptAll();
        String prompt = messageProvider.getMessage("user_input.author_new_name");
        String newAuthorName = userInteractionService.getTextWithPrompt(prompt);
        author.setName(newAuthorName);
        authorRepository.save(author);
    }
}
