package ru.otus.filinovich.service.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.filinovich.domain.User;
import ru.otus.filinovich.service.io.IoService;
import ru.otus.filinovich.utils.PropertiesProvider;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    @Getter
    private final User user = new User();

    private final IoService ioService;

    private final PropertiesProvider propertiesProvider;

    @Override
    public boolean isLogin() {
        return user.getName() != null && user.getSurname() != null;
    }

    @Override
    public void clearUser() {
        user.setSurname(null);
        user.setName(null);
    }

    @Override
    public String getSurnameAndName() {
        return user.getSurname() + " " + user.getName();
    }
}
