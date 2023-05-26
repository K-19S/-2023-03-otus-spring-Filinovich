package ru.otus.filinovich.service.author;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.filinovich.dao.author.AuthorDao;
import ru.otus.filinovich.domain.Author;
import ru.otus.filinovich.service.IOService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    private final IOService ioService;

    @Override
    public List<Author> getAllAuthors() {
        return authorDao.getAll();
    }

    @Override
    public Author getAuthorById(Long id) {
        return authorDao.getById(id);
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
