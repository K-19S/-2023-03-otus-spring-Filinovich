package ru.otus.filinovich.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.otus.filinovich.configs.LocaleProvider;

@Component
@RequiredArgsConstructor
public class PropertiesProvider {

    private final MessageSource messageSource;

    private final LocaleProvider localeProvider;

    public String getMessage(String placeholder) {
        return messageSource.getMessage(placeholder, new Object[]{}, localeProvider.getLocale());
    }

    public String getMessage(String placeholder, Object... args) {
        return messageSource.getMessage(placeholder, args, localeProvider.getLocale());
    }
}
