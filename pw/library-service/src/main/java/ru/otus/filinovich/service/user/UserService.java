package ru.otus.filinovich.service.user;

import ru.otus.filinovich.domain.User;

public interface UserService {

    User getUserByUsername(String id);

    User save(User user);
}
