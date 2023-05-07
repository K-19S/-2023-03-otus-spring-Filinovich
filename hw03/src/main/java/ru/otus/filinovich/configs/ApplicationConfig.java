package ru.otus.filinovich.configs;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.filinovich.service.io.IoService;
import ru.otus.filinovich.service.io.IoServiceImpl;

@Configuration
@ConfigurationPropertiesScan
public class ApplicationConfig {

    @Bean
    public IoService ioServiceConsole() {
        return new IoServiceImpl(System.out, System.in);
    }
}
