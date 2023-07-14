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
}
