package ru.innopolis.stc12.lab.wordFinder;

import ru.innopolis.stc12.lab.wordFinder.Parser.Parser;
import ru.innopolis.stc12.lab.wordFinder.Utils.FileExtension;
import ru.innopolis.stc12.lab.wordFinder.Utils.PatternsArray;
import ru.innopolis.stc12.lab.wordFinder.Utils.SaveResult;
import ru.innopolis.stc12.lab.wordFinder.Utils.ThreadCounter;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

public class DataParser implements DataParserInterface {
    private Parser[] parserPool;
    private ReentrantLock locker = new ReentrantLock();
    private ThreadCounter threadCounter = new ThreadCounter();

    @Override
    public void getOccurrences(String[] sources, String[] words, String res) throws IOException, InterruptedException, EmptySourceException {
        checkSourcesEmpty(sources, words, res);
        runDataParserPool(sources, words);
        waitParserPool();
        saveToFile(res, parserPool);
    }

    private void saveToFile(String fileName, Parser[] sentences) {
        new SaveResult(fileName, sentences);
    }

    private void checkSourcesEmpty(String[] sources, String[] words, String res) throws EmptySourceException {
        checkArrayEmpty(sources);
        checkArrayEmpty(words);
        checkStringEmpty(res);
    }

    private void checkStringEmpty(String str) throws EmptySourceException {
        if ((str == null) | (str.equals(""))) throw new EmptySourceException(str);
    }

    private void checkArrayEmpty(String[] array) throws EmptySourceException {
        if (array == null || Arrays.asList(array).contains(null) || Arrays.asList(array).contains(""))
            throw new EmptySourceException(array.toString());
    }

    private void waitParserPool() {
        while (threadCounter.getCount() > 0)
            threadSleep();
    }

    private void threadSleep() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void runDataParserPool(String[] sources, String[] words) {
        parserPool = new Parser[sources.length];

        for (int i = 0; i < parserPool.length; i++) {
            new Thread(parserPool[i] = new Parser(
                    patterns(words), new FileExtension(sources[i]), threadCounter, locker)).start();
            while (threadCounter.getCount() >= 4) {
                threadSleep();
            }
        }
    }

    private Pattern[] patterns(String[] words) {
        return new PatternsArray(words).getPatterns();
    }


}
