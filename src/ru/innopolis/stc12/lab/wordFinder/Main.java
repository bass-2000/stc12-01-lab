package ru.innopolis.stc12.lab.wordFinder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws EmptySourceException, IOException, InterruptedException {
        ArrayList<String> resourcesList = new ArrayList<>();


        try (Scanner sc = new Scanner(new File("resources"))) {

            while (sc.hasNext()) {
                String line = sc.nextLine();
                resourcesList.add(line);
            }

            String[] words = {"слово"};

            String resultFile = "result";

            String[] sources = resourcesList.toArray(new String[resourcesList.size()]);
            System.out.println("Количество ресурсов: " + sources.length);
            new DataParser().getOccurrences(sources, words, resultFile);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
