package ru.innopolis.stc12.lab.wordFinder.Parser;

import ru.innopolis.stc12.lab.wordFinder.Utils.FileExtension;
import ru.innopolis.stc12.lab.wordFinder.Utils.StringParserCounter;
import ru.innopolis.stc12.lab.wordFinder.Utils.ThreadCounter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

public class Parser implements Runnable {
    private Pattern[] patterns;
    private FileExtension fileExtension;
    private ThreadCounter threadCounter;
    private ReentrantLock lock;
    private ReentrantLock stringParserLock = new ReentrantLock();
    private Set<String> result = new HashSet<>();
    private StringParserCounter stringParserCounter = new StringParserCounter();
    private int limit = 5000;
    private String[] stringsToParse = new String[limit];
    private int count = 0;

    public Parser(Pattern[] patterns, FileExtension fileExtension, ThreadCounter threadCounter, ReentrantLock lock) {
        this.patterns = patterns;
        this.fileExtension = fileExtension;
        this.threadCounter = threadCounter;
        this.lock = lock;
    }

    public Set<String> getResult() {
        return result;
    }

    @Override
    public void run() {
        countPlus();
        readFile();
        while (stringParserCounter.getCount() > 0)
            sleep();
        countMinus();
    }

    private void readFile() {
        try (Scanner scanner = fileExtension.getFileContent()) {
            while (scanner.hasNext()) {
                prepareStringsToParse(scanner.nextLine());
                while (stringParserCounter.getCount() > 0)
                    sleep();
            }
            checkTail();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void prepareStringsToParse(String lineFromScanner) {
        lineFromScanner.toLowerCase();
        stringsToParse[count] = lineFromScanner;
        count++;
        if (count == limit) {
            newAnalyzerThread(stringsToParse);
            stringsToParse = new String[limit];
            count = 0;
        }
    }

    private void checkTail() {
        if (count > 0) {
            String[] tailLinesForAnalisys = Arrays.copyOf(stringsToParse, count);
            newAnalyzerThread(tailLinesForAnalisys);
        }
    }

    private void newAnalyzerThread(String[] stringsArray) {
        new Thread(
                new StringParser(stringsArray, patterns, result, stringParserLock, stringParserCounter)
        ).start();
    }

    private void sleep() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void countMinus() {
        lock.lock();
        threadCounter.setCount(threadCounter.getCount() - 1);
        lock.unlock();
    }

    private void countPlus() {
        lock.lock();
        threadCounter.setCount(threadCounter.getCount() + 1);
        lock.unlock();
    }


}
