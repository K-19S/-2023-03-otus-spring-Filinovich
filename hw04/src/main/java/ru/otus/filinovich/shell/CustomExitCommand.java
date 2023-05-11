package ru.otus.filinovich.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.commands.Quit;
import ru.otus.filinovich.service.io.IoService;
import ru.otus.filinovich.utils.PropertiesProvider;

@ShellComponent
@RequiredArgsConstructor
public class CustomExitCommand implements Quit.Command {

    private final IoService ioService;

    private final PropertiesProvider propertiesProvider;

    @ShellMethod(value = "Exit the shell.", key = "exit")
    public void exit() {
        String message = propertiesProvider.getMessage("shell.goodbye_message");
        ioService.outputString(message);
        System.exit(0);
    }
}
