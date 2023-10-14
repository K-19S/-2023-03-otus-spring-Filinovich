package ru.otus.filinovich.resilince.clients;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.otus.filinovich.resilince.request.LoginRequest;
import ru.otus.filinovich.resilince.request.SignupRequest;
import ru.otus.filinovich.resilince.response.JwtResponse;
import ru.otus.filinovich.resilince.response.MessageResponse;

@Component
@RequiredArgsConstructor
public class AuthClient extends AbstractClient {

    private final RestTemplate restTemplate;

    private final CircuitBreakerFactory circuitBreakerFactory;


    public ResponseEntity<?> authorize(LoginRequest loginRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginRequest> entity = new HttpEntity<>(loginRequest, headers);
        return circuitBreakerFactory.create("signin").run(
                () -> restTemplate.exchange("http://authentication-service/api/auth/signin",
                        HttpMethod.POST, entity, JwtResponse.class),
                throwable -> ResponseEntity.badRequest().build());
    }

    public ResponseEntity<?> registerUser(SignupRequest signupRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SignupRequest> entity = new HttpEntity<>(signupRequest, headers);
        return circuitBreakerFactory.create("signup").run(
                () -> restTemplate.exchange("http://authentication-service/api/auth/signup",
                        HttpMethod.POST, entity, MessageResponse.class),
                throwable -> fallbackError());
    }

    private ResponseEntity<?> fallbackError() {
        return ResponseEntity.status(429).build();
    }
}
