package ru.innopolis.stc12.lab.wordFinder;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.log4j.Logger;


public class DataParser implements DataParserInterface {
    ArrayList<SingleResourceParser> threads = new ArrayList<>();
    private ReaderWriter readerWriter;
    final static Logger logger = Logger.getLogger(DataParser.class);

    public DataParser(ReaderWriter readerWriter) {
        this.readerWriter = readerWriter;
    }

    @Override
    public void getOccurrences(String[] sources, String[] words, String res) throws IOException, InterruptedException, EmptySourceException {
        if (!readerWriter.isEmpty(new Object[]{sources, words, res}, new String[]{"массив ресурсов", "массив слов", "файл"})) {
            checkAndCleaning(res);
            Timer timer = new Timer(System.currentTimeMillis());
            Counter counter = new Counter(threads);
            for (int i = 0; i < sources.length; i++) {
                boolean isBig = isBigSource(sources[i]);
                SingleResourceParser singleResourceParser = new SingleResourceParser("Thread " + i, sources[i], isBig, res, words, readerWriter, counter);
                threads.add(singleResourceParser);
                singleResourceParser.start();
            }
            counter.start();
        }
    }

    private void checkAndCleaning(String filename) {
        File checkFile = new File(filename);
        if (checkFile.exists()) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
                bw.write("");
                bw.flush();
            } catch (IOException e) {
                logger.error(e);
            }
        } else {
            try {
                checkFile.createNewFile();
            } catch (IOException e) {
                logger.error(e);
            }
        }
    }

    private boolean isBigSource(String filenameSource) {
        File f = new File(filenameSource);
        return (f.length() > 100000);
    }
}