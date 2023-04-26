package ru.otus.filinovich.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public interface Service {

    default BufferedReader getConsoleReader() {
        return new BufferedReader(new InputStreamReader(System.in));
    }
}
