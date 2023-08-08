package ru.otus.filinovich.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document("users")
@Getter
public class User {

    @Id
    private String id;

    private final String username;

    private final String password;

    private final Set<UserRole> userRoles;

    public User(String username, String password, Set<UserRole> userRoles) {
        this.username = username;
        this.password = password;
        this.userRoles = userRoles;
    }
}
