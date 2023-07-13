package ru.otus.filinovich.service.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.filinovich.domain.User;
import ru.otus.filinovich.service.io.IoService;
import ru.otus.filinovich.utils.PropertiesProvider;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Getter
    private final User user = new User();

    private final IoService ioService;

    private final PropertiesProvider propertiesProvider;

    @Override
    public void askUserSurnameAndName() {
        var enterSurnameMessage = propertiesProvider.getMessage("user.enter_surname_message");
        String surname = ioService.readStringWithPromt(enterSurnameMessage);
        user.setSurname(surname);

        var enterNameMessage = propertiesProvider.getMessage("user.enter_name_message");
        String name = ioService.readStringWithPromt(enterNameMessage);
        user.setName(name);
    }
}
