package ru.innopolis.stc12.lab.wordFinder;

public class EmptySourceException extends Exception {
    private String source;

    public EmptySourceException(String source) {
        System.out.println("Empty source: " + source);
    }
}
