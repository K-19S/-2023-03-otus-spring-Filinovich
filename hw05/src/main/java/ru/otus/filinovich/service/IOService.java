package ru.otus.filinovich.service;

public interface IOService {

    void outputString(String text);

    String readString();

    Long readLong();

    Long readLong(Long min, Long max);

    String readStringWithPrompt(String promt);

    Long readLongWithPrompt(String promt);
}
