package ru.innopolis.stc12.lab.wordFinder;

import org.apache.log4j.Logger;

public class SingleResourceParser extends Thread {
    final static Logger logger = Logger.getLogger(SingleResourceParser.class);
    String name;
    String fileSource;
    boolean isBigFile;
    String fileResult;
    String[] words;
    ReaderWriter readerWriter;
    Counter counter;

    public SingleResourceParser(String name, String fileSource, boolean isBigFile, String fileResult, String[] words, ReaderWriter readerWriter, Counter counter) {
        this.name = name;
        this.fileSource = fileSource;
        this.isBigFile = isBigFile;
        this.fileResult = fileResult;
        this.words = words;
        this.readerWriter = readerWriter;
        this.counter = counter;
    }

    @Override
    public void run() {
        while (counter.getCount() > 7) {
            synchronized (counter) {
                try {
                    counter.wait();
                } catch (InterruptedException e) {
                    logger.error(e);
                }
            }
        }
        counter.incCount();
        logger.info(name + " создан новый поток");
        String[] result;
        if (!isBigFile) {
            String fromFile = readerWriter.readFrom(fileSource);
            String[] temp = readerWriter.writeToSentences(fromFile);
            result = readerWriter.wordSearch(temp, words);
        } else {
            logger.info("Внимание! Идет обработка файла большого размера. Это может занять несколько (от 3 до 7) минут");
            result = readerWriter.readFromBigFile(fileSource, words);
        }
        readerWriter.writeToResult(result, fileResult);
        logger.info(name + " поток завершен");
        counter.decCount();
        synchronized (counter) {
            counter.notifyAll();
        }

    }
}

