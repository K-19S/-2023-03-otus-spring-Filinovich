package ru.otus.filinovich.service.io;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class IoServiceImpl implements IoService{

    private final PrintStream printStream;

    private final Scanner scanner;

    public IoServiceImpl(PrintStream os, InputStream is) {
        this.printStream = os;
        this.scanner = new Scanner(is);
    }

    @Override
    public void outputString(String s) {
        printStream.println(s);
    }

    @Override
    public String readString() {
        return scanner.nextLine();
    }

    @Override
    public String readStringWithPromt(String promt) {
        outputString(promt);
        return readString();
    }
}
