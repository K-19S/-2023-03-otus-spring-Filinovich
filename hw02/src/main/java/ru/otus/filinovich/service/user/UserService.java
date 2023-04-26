package ru.otus.filinovich.service.user;

import ru.otus.filinovich.domain.User;
import ru.otus.filinovich.service.Service;

import java.io.BufferedReader;
import java.io.IOException;

public interface UserService extends Service {

    void askUserSurnameAndName(BufferedReader reader) throws IOException;

    User getUser();
}
