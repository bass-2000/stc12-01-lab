package ru.innopolis.stc12.lab.wordFinder;


import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.*;


public class DataParser implements DataParserInterface {

    private static final Logger logger = Logger.getLogger(DataParser.class);
    private static final int MAX_T = 20;
    private ExecutorService pool;


    public DataParser() {
        pool = Executors.newFixedThreadPool(MAX_T);
    }

    @Override
    public void getOccurrences(String[] sources, String[] words, String res) throws InterruptedException {
        long beginTime = System.currentTimeMillis();
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        Thread fileWriter = new Thread(() -> {
            try (PrintWriter writer = new PrintWriter(new File(res));) {
                while (true) {
                    writer.println(queue.take());
                }
            } catch (IOException | InterruptedException e) {
                logger.error(e.getMessage());
            }
        });
        fileWriter.start();
        logger.info("Writer was started");
        for (String source : sources) {
            pool.execute(new SingleResourceParser(source, words, queue));
            logger.info("Job executed for source: " + source);
        }
        pool.shutdown();
        pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        fileWriter.interrupt();
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