package ru.otus.filinovich.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@ConfigurationProperties(prefix = "application")
@Setter
@Getter
public class AppProps implements LocaleProvider, TestingProps {

    private Locale locale;

    private Integer requiredNumberOfRightAnswers;
}
