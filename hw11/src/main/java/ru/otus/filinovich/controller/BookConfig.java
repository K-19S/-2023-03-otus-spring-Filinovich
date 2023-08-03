package ru.otus.filinovich.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.filinovich.dto.BookDto;
import ru.otus.filinovich.service.book.BookService;

import static org.springframework.web.reactive.function.server.RequestPredicates.queryParam;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.status;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;

@Configuration
public class BookConfig {

    @Bean
    public RouterFunction<ServerResponse> booksRouters(BookService bookService) {
        return route()
                .GET("/books", request ->
                    ok().body(bookService.getAllBooks()
                        .map(BookDto::toDto), BookDto.class)
                )
                .POST("/books", request -> request.bodyToMono(BookDto.class)
                    .map(BookDto::fromDto)
                    .flatMap(book -> bookService.save(Mono.just(book)))
                    .flatMap(book -> status(201).body(Mono.just(BookDto.toDto(book)), BookDto.class))
                    .switchIfEmpty(badRequest().build())
                )
                .PUT("/books", request -> request.bodyToMono(BookDto.class)
                    .map(BookDto::fromDto)
                    .flatMap(book -> bookService.update(Mono.just(book)))
                    .flatMap(book -> ok().body(Mono.just(BookDto.toDto(book)), BookDto.class))
                    .switchIfEmpty(badRequest().build())
                )
                .DELETE("/books", queryParam("id", StringUtils::isNotEmpty),
                    request -> request.queryParam("id")
                        .map(id -> ok().body(bookService.deleteById(id), Object.class))
                        .orElse(badRequest().build())
                ).build();
    }
}
