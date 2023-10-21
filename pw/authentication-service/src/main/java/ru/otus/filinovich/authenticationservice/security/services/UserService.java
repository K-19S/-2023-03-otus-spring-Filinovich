package ru.otus.filinovich.authenticationservice.security.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.filinovich.authenticationservice.models.User;
import ru.otus.filinovich.authenticationservice.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void banUser(String id) {
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(user1 -> {
            user1.setBanned(true);
            userRepository.save(user1);
        });
    }

    public void unbanUser(String id) {
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(user1 -> {
            user1.setBanned(false);
            userRepository.save(user1);
        });
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
