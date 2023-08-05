package ru.otus.filinovich.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.filinovich.dto.GenreDto;
import ru.otus.filinovich.service.genre.GenreService;

import static org.springframework.web.reactive.function.server.RequestPredicates.queryParam;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.status;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;

@Configuration
public class GenreConfig {

    @Bean
    public RouterFunction<ServerResponse> genreRouters(GenreService genreService) {
        return route()
                .GET("/genres", request ->
                    ok().body(genreService.getAllGenres()
                        .map(GenreDto::toDto), GenreDto.class)
                )
                .POST("/genres", request -> request.bodyToMono(GenreDto.class)
                    .map(GenreDto::fromDto)
                    .flatMap(genre -> genreService.save(Mono.just(genre)))
                    .flatMap(genre -> status(201).body(Mono.just(GenreDto.toDto(genre)), GenreDto.class))
                    .switchIfEmpty(badRequest().build())
                )
                .PUT("/genres", request -> request.bodyToMono(GenreDto.class)
                    .map(GenreDto::fromDto)
                    .flatMap(genre -> genreService.update(Mono.just(genre)))
                    .flatMap(genre -> ok().body(Mono.just(GenreDto.toDto(genre)), GenreDto.class))
                    .switchIfEmpty(badRequest().build())
                )
                .DELETE("/genres", queryParam("id", StringUtils::isNotEmpty),
                    request -> request.queryParam("id")
                        .map(id -> ok().body(genreService.deleteById(id), Object.class))
                        .orElse(badRequest().build())
                ).build();
    }
}
