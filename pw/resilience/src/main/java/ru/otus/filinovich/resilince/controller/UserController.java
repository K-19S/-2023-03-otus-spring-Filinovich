package ru.otus.filinovich.resilince.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.filinovich.resilince.clients.UserClient;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserClient client;

    @GetMapping("/userBooks")
    public ResponseEntity<Set> getUserBooks(@RequestHeader String authorization) {
        return client.getUserBooks(authorization);
    }

    @PostMapping("/addUserbook")
    public ResponseEntity<Set> addUserbook(@RequestParam String bookId, @RequestHeader String authorization) {
        return client.addUserbook(bookId, authorization);
    }

    @DeleteMapping("/deleteMyBook")
    public ResponseEntity<Set> deleteUserBook(@RequestParam String id, @RequestHeader String authorization) {
        return client.deleteUserBook(id, authorization);
    }

    @GetMapping("/users")
    public ResponseEntity<List> getAllUsers(@RequestHeader String authorization) {
        return client.getAllUsers(authorization);
    }

    @PostMapping("/users/ban")
    public ResponseEntity<List> banUser(@RequestParam String id, @RequestHeader String authorization) {
        return client.banUser(id, authorization);
    }

    @PostMapping("/users/unban")
    public ResponseEntity<List> unbanUser(@RequestParam String id, @RequestHeader String authorization) {
        return client.unbanUser(id, authorization);
    }

    @DeleteMapping("/users")
    public ResponseEntity<List> deleteUser(@RequestParam String id, @RequestHeader String authorization) {
        return client.deleteUser(id, authorization);
    }
}
