package ru.innopolis.stc12.lab.wordFinder;

import java.util.ArrayList;
import org.apache.log4j.Logger;

public class Counter extends Thread {
    final static Logger currentClassLogger = Logger.getLogger(Counter.class);
    final static Logger loggerSRP = Logger.getLogger(SingleResourceParser.class);
    ArrayList<SingleResourceParser> threads = new ArrayList<>();
    Timer timer;
    private int count;


    public Counter(ArrayList<SingleResourceParser> threads) {
        this.threads = threads;
        count = 0;
        timer = new Timer(System.currentTimeMillis());
        currentClassLogger.info("Создан поток-счетчик Counter");
    }

    public synchronized int getCount() {
        return count;
    }

    public synchronized void incCount() {
        count++;
        loggerSRP.info(count + " потоков выполняется");
    }

    public synchronized void decCount() {
        if (count > 0) count--;
        loggerSRP.info(count + " потоков выполняется");
    }

    @Override
    public void run() {
        for (int i = 0; i < threads.size(); i++) {
            try {
                threads.get(i).join();
            } catch (InterruptedException e) {
                currentClassLogger.error(e);
            }
        }
        timer.calculateTime();
        currentClassLogger.info("Counter завершен");
    }
}