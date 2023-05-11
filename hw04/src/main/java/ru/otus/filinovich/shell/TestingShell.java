package ru.otus.filinovich.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.filinovich.service.testing.TestingService;
import ru.otus.filinovich.service.user.UserService;
import ru.otus.filinovich.utils.PropertiesProvider;

@ShellComponent
@RequiredArgsConstructor
public class TestingShell {

    private final TestingService testingService;

    private final UserService userService;

    private final PropertiesProvider propertiesProvider;

    @ShellMethod(value = "Starting the testing process")
    @ShellMethodAvailability("checkUserIsLogin")
    public String start() {
        testingService.startTest();
        return propertiesProvider.getMessage("shell.testing_completed_message");
    }

    @ShellMethod(value = "Login user (requires 2 parameters \"surname\" and \"name\"")
    public String login(@ShellOption String surname, @ShellOption String name) {
        userService.getUser().setSurname(surname);
        userService.getUser().setName(name);
        return propertiesProvider.getMessage("shell.message_after_login", userService.getSurnameAndName());
    }

    @ShellMethod(value = "Logout user")
    public String logout() {
        if (userService.isLogin()) {
            userService.clearUser();
            return propertiesProvider.getMessage("shell.message_after_logout");
        } else {
            return propertiesProvider.getMessage("shell.message_failed_logout");
        }
    }

    private Availability checkUserIsLogin() {
        if (userService.isLogin()) {
            return Availability.available();
        } else {
            String message = propertiesProvider.getMessage("shell.login_required_message");
            return Availability.unavailable(message);
        }
    }
}
