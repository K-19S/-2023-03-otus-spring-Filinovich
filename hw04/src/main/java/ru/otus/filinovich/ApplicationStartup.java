package ru.otus.filinovich;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.otus.filinovich.service.io.IoService;
import ru.otus.filinovich.utils.PropertiesProvider;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class ApplicationStartup {

    private final IoService ioService;

    private final PropertiesProvider propertiesProvider;

    @EventListener(ApplicationStartedEvent.class)
    public void startUpMessage() {
        String greetingMessage1 = propertiesProvider.getMessage("shell.greeting1");
        ioService.outputString(greetingMessage1);
        String greetingMessage2 = propertiesProvider.getMessage("shell.greeting2");
        ioService.outputString(greetingMessage2);
    }
}
