package ru.innopolis.stc12.lab.wordFinder;

public class Timer {
    long startTime;

    public Timer(long startTime) {
        this.startTime = startTime;
    }

    public void calculateTime() {
        long finishTime = System.currentTimeMillis();
        System.out.println("Время выполнения поиска: " + (finishTime - startTime));
    }
}