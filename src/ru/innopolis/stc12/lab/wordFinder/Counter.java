package ru.innopolis.stc12.lab.wordFinder;

import java.util.ArrayList;

public class Counter extends Thread {
    ArrayList<SingleResourceParser> threads = new ArrayList<>();
    Timer timer;
    private int count;


    public Counter(ArrayList<SingleResourceParser> threads) {
        this.threads = threads;
        count = 0;
        timer = new Timer(System.currentTimeMillis());
        System.out.println("Создан поток-счетчик Counter");
    }

    public synchronized int getCount() {
        return count;
    }

    public synchronized void incCount() {
        count++;
        System.out.println(count + " потоков выполняется");
    }

    public synchronized void decCount() {
        if (count > 0) count--;
        System.out.println(count + " потоков выполняется");
    }

    @Override
    public void run() {
        for (int i = 0; i < threads.size(); i++) {
            try {
                threads.get(i).join();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        timer.calculateTime();
        System.out.println("Counter завершен");
    }
}