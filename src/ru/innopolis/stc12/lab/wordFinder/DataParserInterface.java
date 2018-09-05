package ru.innopolis.stc12.lab.wordFinder;

import java.io.IOException;

public interface DataParserInterface {

    void getOccurrences(String[] sources, String[] words, String res) throws IOException, InterruptedException, EmptySourceException;
}

