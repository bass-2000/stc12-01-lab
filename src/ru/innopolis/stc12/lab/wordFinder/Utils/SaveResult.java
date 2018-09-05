package ru.innopolis.stc12.lab.wordFinder.Utils;

import ru.innopolis.stc12.lab.wordFinder.Parser.Parser;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;


public class SaveResult {
    public SaveResult(String filename, Parser[] parsers) {

        Set<String> storage = convertToSentencesSet(parsers);

        try (ObjectOutputStream bOutputStream = new ObjectOutputStream(new FileOutputStream(filename))) {

            bOutputStream.writeObject(storage);

            System.out.println("Сохранение успешно");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private Set<String> convertToSentencesSet(Parser[] sentences) {

        Set<String> result = new HashSet<>();
        for (Parser p : sentences)
            result.addAll(p.getResult());

        printResult(result.size());

        return result;
    }

    private void printResult(int i) {
        System.out.println("\nБыло отобрано " + i + " предложений");
    }
}
