package ru.otus.filinovich.service.io;

public interface IOService {

    void outputString(String text);

    String readString();

    Long readLong();

    Long readLong(int min, int max);

    String readStringWithPrompt(String promt);

    Long readLongWithPrompt(String promt);
}
