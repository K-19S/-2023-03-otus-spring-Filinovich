package ru.otus.filinovich.service.files.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.filinovich.dao.file.FileRepository;
import ru.otus.filinovich.domain.Book;
import ru.otus.filinovich.domain.BookFile;
import ru.otus.filinovich.service.files.FileService;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    @Override
    public void save(BookFile file) {
        fileRepository.save(file);
    }

    @Override
    public BookFile getByBook(Book book) {
        return fileRepository.getByBook(book);
    }
}
