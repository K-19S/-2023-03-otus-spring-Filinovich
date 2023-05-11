package ru.otus.filinovich.service.user;

import ru.otus.filinovich.domain.User;

public interface UserService {

    boolean isLogin();

    void clearUser();

    User getUser();

    String getSurnameAndName();
}
