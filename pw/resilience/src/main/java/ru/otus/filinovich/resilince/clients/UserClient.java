package ru.otus.filinovich.resilince.clients;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserClient extends AbstractClient {

    private final RestTemplate restTemplate;

    private final CircuitBreakerFactory circuitBreakerFactory;

    public ResponseEntity<Set> getUserBooks(String authorization) {
        return circuitBreakerFactory.create("getUserBooks").run(
                () -> restTemplate.exchange("http://library/userBooks",
                        HttpMethod.GET, getEntity(authorization), Set.class),
                throwable -> ResponseEntity.ok(new HashSet<>()));
    }

    public ResponseEntity<Set> addUserbook(String id, String authorization) {
        return circuitBreakerFactory.create("addUserBook").run(
                () -> restTemplate.exchange("http://library/addUserbook?bookId=" + id,
                        HttpMethod.POST, getEntity(authorization), Set.class),
                throwable -> ResponseEntity.ok(new HashSet<>()));
    }

    public ResponseEntity<Set> deleteUserBook(String id, String authorization) {
        return circuitBreakerFactory.create("deleteUserBook").run(
                () -> restTemplate.exchange("http://library/deleteUserBook?id=" + id,
                        HttpMethod.DELETE, getEntity(authorization), Set.class),
                throwable -> ResponseEntity.ok(new HashSet<>()));
    }

    public ResponseEntity<List> getAllUsers(String authorization) {
        return circuitBreakerFactory.create("getAllUsers").run(
                () -> restTemplate.exchange("http://authentication-service/users",
                        HttpMethod.GET, getEntity(authorization), List.class),
                throwable -> ResponseEntity.ok(new ArrayList<>()));
    }

    public ResponseEntity<List> banUser(String id, String authorization) {
        return circuitBreakerFactory.create("banUser").run(
                () -> restTemplate.exchange("http://authentication-service/users/ban?id=" + id,
                        HttpMethod.POST, getEntity(authorization), List.class),
                throwable -> ResponseEntity.ok(new ArrayList<>()));
    }

    public ResponseEntity<List> unbanUser(String id, String authorization) {
        return circuitBreakerFactory.create("unbanUser").run(
                () -> restTemplate.exchange("http://authentication-service/users/unban?id=" + id,
                        HttpMethod.POST, getEntity(authorization), List.class),
                throwable -> ResponseEntity.ok(new ArrayList<>()));
    }

    public ResponseEntity<List> deleteUser(String id, String authorization) {
        return circuitBreakerFactory.create("deleteUser").run(
                () -> restTemplate.exchange("http://authentication-service/users?id=" + id,
                        HttpMethod.DELETE, getEntity(authorization), List.class),
                throwable -> ResponseEntity.ok(new ArrayList<>()));
    }


    private ResponseEntity<?> fallbackError() {
        return ResponseEntity.status(429).build();
    }
}
