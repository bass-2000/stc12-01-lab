package ru.innopolis.stc12.lab.wordFinder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {


    public static void main(String[] args) throws EmptySourceException, IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        String resultFile = "result";
        String[] sourcesArray = getFIleNames("D://temp//testSet//");
        String[] words = {"starter", "smarter"};


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


    public static String[] getFIleNames(String path) {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        List<String> results = new ArrayList<>();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                results.add(path + listOfFiles[i].getName());
            }
        }
        return results.toArray(new String[0]);
    }
}



