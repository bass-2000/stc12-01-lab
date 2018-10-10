package ru.innopolis.stc12.lab.wordFinder;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.BlockingQueue;

public class SingleResourceParser implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(SingleResourceParser.class);
    private static String[] words;
    private static BlockingQueue<String> queue;
    private String path;

    SingleResourceParser(String path, String[] wordsList, BlockingQueue<String> queueInstance) {
        this.path = path;
        words = wordsList;
        queue = queueInstance;
    }

    private static void compareWithWordsAndSave(String sentence) throws InterruptedException {
        for (String w : words) {
            if (sentence.toLowerCase().contains(w.toLowerCase())) {
                queue.put(sentence);
                break;
            }
        }
    }

    @Override
    public void run() {
        URL url = this.getUrl();
        if (url != null) {
            try (InputStream inputStream = url.openStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));) {
                StringBuilder buffer = new StringBuilder();
                int data = 0;
                while ((data = reader.read()) != -1) {
                    char symbol = (char) data;
                    if (symbol == '.' || symbol == '!' || symbol == '?') {
                        compareWithWordsAndSave(buffer.toString());
                        buffer = new StringBuilder();
                    } else {
                        buffer.append(symbol);
                    }
                }
            } catch (IOException | InterruptedException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    private URL getUrl() {
        URL url = null;
        try {
            url = new URL(this.path);
            LOGGER.info("URL created: " + url.toString());
        } catch (MalformedURLException e) {
            LOGGER.error(e.getMessage());
        }
        return url;
    }
}