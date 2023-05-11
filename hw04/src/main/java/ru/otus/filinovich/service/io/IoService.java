package ru.otus.filinovich.service.io;

public interface IoService {

    void outputString(String s);

    String readString();

    String readStringWithPromt(String promt);
}
