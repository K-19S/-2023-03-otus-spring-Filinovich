package ru.otus.filinovich.controller;

import io.micrometer.common.util.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.filinovich.dto.BookCommentDto;
import ru.otus.filinovich.service.book_comment.BookCommentService;

import static org.springframework.web.reactive.function.server.RequestPredicates.queryParam;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.status;

@Configuration
public class BookCommentConfig {

    @Bean
    public RouterFunction<ServerResponse> commentRouters(BookCommentService bookCommentService) {
        return route()
                .GET("/comments", queryParam("bookId", StringUtils::isNotEmpty),
                    request ->
                        request.queryParam("bookId")
                            .map(bookId ->
                                ok().body(bookCommentService.getAllCommentsByBookId(bookId)
                                .map(BookCommentDto::toDto), BookCommentDto.class))
                                .orElse(badRequest().build()))
                .POST("/comments", request ->
                    request.bodyToMono(BookCommentDto.class)
                        .flatMap(comment -> bookCommentService.createComment(
                            Mono.just(BookCommentDto.fromDto(comment)),
                            comment.getBookId()))
                        .flatMap(comment -> status(201).body(
                                Mono.just(BookCommentDto.toDto(comment)), BookCommentDto.class))
                        .switchIfEmpty(badRequest().build())
                ).build();
    }
}
