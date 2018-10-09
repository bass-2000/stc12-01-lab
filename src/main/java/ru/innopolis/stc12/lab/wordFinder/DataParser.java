package ru.innopolis.stc12.lab.wordFinder;


import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class DataParser implements DataParserInterface {
    private List<Future<String>> future = new ArrayList<>();
    final static Logger logger = Logger.getLogger(DataParser.class);

    public DataParser(String[] sources, String[] words, String res) {
        getOccurrences(sources, words, res);
    }

    @Override
    public void getOccurrences(String[] sources, String[] words, String res) {
        logger.info("getOccurrences start");
        long beginTime = System.currentTimeMillis();
        SingleResourceParser singleResourceParser = new SingleResourceParser();
        Writer writer = new Writer();
        ExecutorService threadPool = Executors.newFixedThreadPool(23);
        logger.info("getSentence start");
        for (String str : sources) {
            if (str != null) {
                future.add(CompletableFuture.supplyAsync(() -> singleResourceParser.getSentence(str, words), threadPool));
            }
        }
        logger.info("getSentence end");
        logger.info("writer start");
        writer.write(future, res);
        logger.info("shutdown start");
        threadPool.shutdown();
        calculateTime(beginTime);
    }

    private void calculateTime(long beginTime) {
        long finishTime = System.currentTimeMillis();
        long overageTime = finishTime - beginTime;
        int overageTimeInMinutes = 0;
        while (overageTime > 60000L) {
            overageTime = overageTime - 60000L;
            overageTimeInMinutes++;
        }
        int overageTimeInSeconds = 0;
        while (overageTime > 1000L) {
            overageTime = overageTime - 1000L;
            overageTimeInSeconds++;
        }
        logger.info("Время выполнения поиска: " + (finishTime - beginTime));
        logger.info("Время выполнения поиска: " + overageTimeInMinutes + " минут " + overageTimeInSeconds + "  секунд\n");
    }
}