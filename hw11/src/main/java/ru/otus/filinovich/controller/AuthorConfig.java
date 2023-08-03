package ru.otus.filinovich.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.filinovich.dto.AuthorDto;
import ru.otus.filinovich.service.author.AuthorService;

import static org.springframework.web.reactive.function.server.RequestPredicates.queryParam;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.status;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;

@Configuration
public class AuthorConfig {

    @Bean
    public RouterFunction<ServerResponse> authorRouters(AuthorService authorService) {
        return route()
            .GET("/authors", request ->
                ok().body(authorService.getAllAuthors()
                    .map(AuthorDto::toDto), AuthorDto.class)
            )
            .POST("/authors", request -> request.bodyToMono(AuthorDto.class)
                .map(AuthorDto::fromDto)
                .flatMap(author -> authorService.save(Mono.just(author)))
                .flatMap(author -> status(201).body(Mono.just(AuthorDto.toDto(author)), AuthorDto.class))
                .switchIfEmpty(badRequest().build())
            )
            .PUT("/authors", request -> request.bodyToMono(AuthorDto.class)
                .map(AuthorDto::fromDto)
                .flatMap(author -> authorService.update(Mono.just(author)))
                .flatMap(author -> ok().body(Mono.just(AuthorDto.toDto(author)), AuthorDto.class))
                .switchIfEmpty(badRequest().build())
            )
            .DELETE("/authors", queryParam("id", StringUtils::isNotEmpty),
                    request -> request.queryParam("id")
                            .map(id -> ok().body(authorService.deleteById(id), Object.class))
                            .orElse(badRequest().build())
            ).build();
    }
}
