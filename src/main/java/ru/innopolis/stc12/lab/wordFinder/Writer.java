package ru.innopolis.stc12.lab.wordFinder;

import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


public class Writer {
    Logger logger = Logger.getLogger(Writer.class);

    public void write(List<Future<String>> future, String res) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(res))) {
            for (Future<String> e : future) {
                writer.write(e.get());
            }
        } catch (IOException | InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
        }
    }
}
