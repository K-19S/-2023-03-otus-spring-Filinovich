package ru.otus.filinovich.service.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.filinovich.dao.user.UserRepository;
import ru.otus.filinovich.domain.User;
import ru.otus.filinovich.service.user.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
