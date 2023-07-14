package ru.otus.filinovich.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.filinovich.service.author.AuthorService;
import ru.otus.filinovich.util.MessageProvider;

@ShellComponent
@RequiredArgsConstructor
public class AuthorShell {

    private final AuthorService authorService;

    private final MessageProvider messageProvider;

    @ShellMethod(value = "Author name update", key = "update-author")
    public String updateAuthorName() {
        authorService.updateAuthor();
        return messageProvider.getMessage("successful_author_updating");
    }
}
