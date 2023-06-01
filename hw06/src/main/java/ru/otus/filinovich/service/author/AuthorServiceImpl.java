package ru.otus.filinovich.service.author;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.filinovich.dao.author.AuthorRepository;
import ru.otus.filinovich.domain.Author;
import ru.otus.filinovich.service.IOService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    private final IOService ioService;

    @Override
    @Transactional(readOnly = true)
    public List<Author> getAllAuthors() {
        return authorRepository.getAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Author getAuthorById(Long id) {
        return authorRepository.getById(id).orElseThrow();
    }

    @Override
    public Author getAuthorByIdWithPromptAll() {
        getAllAuthors().forEach(author -> {
            ioService.outputString(author.getId() + ". " + author.getName());
        });
        Author chosenAuthor = null;
        do {
            Long authorId = ioService.readLong();
            chosenAuthor = getAuthorById(authorId);
        } while (chosenAuthor == null);
        return chosenAuthor;
    }
}
