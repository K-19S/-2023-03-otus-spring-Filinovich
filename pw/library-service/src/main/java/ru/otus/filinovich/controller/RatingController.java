package ru.otus.filinovich.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.filinovich.domain.User;
import ru.otus.filinovich.dto.BookWithRatingDto;
import ru.otus.filinovich.security.service.UserDetailsImpl;
import ru.otus.filinovich.service.rating.RatingService;
import ru.otus.filinovich.service.user.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    private final UserService userService;

    @PostMapping("/setRating")
    public ResponseEntity<?> setRating(@RequestParam String bookId,
                                       @RequestParam Integer rating,
                                       Authentication authentication) {
        User user = userService.getUserByUsername(((UserDetailsImpl) authentication.getPrincipal()).getUsername());
        List<BookWithRatingDto> responseList = ratingService.setRating(bookId, rating, user);
        return ResponseEntity.ok(responseList);
    }
}
