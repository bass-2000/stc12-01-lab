package ru.innopolis.stc12.lab.wordFinder;

import org.apache.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    final static Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("paused. press enter");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        String resultFile = "result";
        String[] sourcesArray = getFIleNames("D://temp//testSet//");
        String[] words = {"starter", "smarter"};
        new DataParser(sourcesArray, words, resultFile);

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



