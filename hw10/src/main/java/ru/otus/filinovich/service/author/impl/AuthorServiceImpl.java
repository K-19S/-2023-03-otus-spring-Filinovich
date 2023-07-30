package ru.otus.filinovich.service.author.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.filinovich.dao.author.AuthorRepository;
import ru.otus.filinovich.domain.Author;
import ru.otus.filinovich.service.author.AuthorService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Author getById(String id) {
        return authorRepository.findById(id).orElseThrow();
    }

    @Override
    public void save(Author author) {
        authorRepository.save(author);
    }

    @Override
    public boolean update(Author author) {
        if (author.getId() == null || author.getId().isBlank()) {
            return false;
        }
        authorRepository.save(author);
        return true;
    }


    @Override
    public void deleteById(String id) {
        authorRepository.deleteById(id);
    }
}
