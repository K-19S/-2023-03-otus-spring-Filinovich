package ru.otus.filinovich.authenticationservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.filinovich.authenticationservice.models.User;
import ru.otus.filinovich.authenticationservice.security.services.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/users/ban")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> banUser(@RequestParam String id) {
        userService.banUser(id);
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/users/unban")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> unbanUser(@RequestParam String id) {
        userService.unbanUser(id);
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> deleteUser(@RequestParam String id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
