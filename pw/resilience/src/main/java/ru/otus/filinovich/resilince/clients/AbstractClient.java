package ru.otus.filinovich.resilince.clients;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

public abstract class AbstractClient {

    protected HttpEntity<?> getEntity(Object body, String authorization) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);
        return new HttpEntity<>(body, headers);
    }

    protected HttpEntity<?> getEntity(String authorization) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);
        return new HttpEntity<>(headers);
    }
}
