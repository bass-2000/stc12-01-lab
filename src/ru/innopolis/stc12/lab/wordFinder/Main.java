package ru.innopolis.stc12.lab.wordFinder;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String[] sources = new String[]{"https://ru.lipsum.com/", "https://ru.wikipedia.org/wiki/Lorem_ipsum"};
        String[] words = {"sex", "drugs", "rock'n'roll"};
        String res = "file:C://TEMP//result.txt";
        DataParser dataParser = new DataParser();
        try {
            dataParser.getOccurrences(sources, words, res);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
