package ru.otus.filinovich.service.user;

import lombok.Getter;
import org.springframework.stereotype.Service;
import ru.otus.filinovich.domain.User;

import java.io.BufferedReader;
import java.io.IOException;

@Service
public class UserServiceImpl implements UserService{

    @Getter
    private final User user = new User();

    public void askUserSurnameAndName(BufferedReader reader) throws IOException {
        System.out.println("Enter your surname: ");
        String surname = readStringData(reader);
        user.setSurname(surname);

        System.out.println("Enter your name: ");
        String name = readStringData(reader);
        user.setName(name);
    }

    private String readStringData(BufferedReader reader) throws IOException {
        String line;
        do {
            line = reader.readLine();
        } while (line == null || line.isBlank());
        return line;
    }
}
