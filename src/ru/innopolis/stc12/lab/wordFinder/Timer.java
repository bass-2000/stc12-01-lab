package ru.innopolis.stc12.lab.wordFinder;

public class Timer {
    long startTime;

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

        System.out.printf("Время выполнения поиска: %d минут %d секунд\n", overageTimeInMinutes, overageTimeInSeconds);
    }
}