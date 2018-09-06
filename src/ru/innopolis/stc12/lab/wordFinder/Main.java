package ru.innopolis.stc12.lab.wordFinder;

import java.io.IOException;


public class Main {


    public static void main(String[] args) throws EmptySourceException, IOException, InterruptedException {
        String resultFile = "result";
        String[] sourcesArray = {"http://www.taobao.com"};
        String[] words = {"requested", "версия"};


        ReaderWriter readerWriter = new ReaderWriter();
        DataParser dataParser = new DataParser(readerWriter);
        try {
            dataParser.getOccurrences(sourcesArray, words, resultFile);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
