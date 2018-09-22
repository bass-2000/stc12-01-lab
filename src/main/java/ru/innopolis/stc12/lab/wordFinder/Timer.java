package ru.innopolis.stc12.lab.wordFinder;

import org.apache.log4j.Logger;

public class Timer {
    long startTime;
    final static Logger logger = Logger.getLogger(Timer.class);

    public Timer(long startTime) {
        this.startTime = startTime;
    }

    public void calculateTime() {
        long finishTime = System.currentTimeMillis();
        long overageTime = finishTime - startTime;
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
        logger.info("Время выполнения поиска: " + (finishTime - startTime));
        logger.info("Время выполнения поиска: " + overageTimeInMinutes + " минут " + overageTimeInSeconds + "  секунд\n");
    }
}