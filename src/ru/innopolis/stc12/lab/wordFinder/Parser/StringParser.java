package ru.innopolis.stc12.lab.wordFinder.Parser;

import ru.innopolis.stc12.lab.wordFinder.Utils.StringParserCounter;

import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringParser implements Runnable {

    private String[] stringsToParse;
    private Pattern[] patterns;
    private Set<String> result;
    private ReentrantLock stringParserLock;
    private StringParserCounter stringParserCounter;

    public StringParser(String[] stringsToParse, Pattern[] patterns, Set<String> result, ReentrantLock stringParserLock, StringParserCounter stringParserCounter) {
        this.stringsToParse = stringsToParse;
        this.patterns = patterns;
        this.result = result;
        this.stringParserLock = stringParserLock;
        this.stringParserCounter = stringParserCounter;
    }

    @Override
    public void run() {
        countPlus();
        findSentences();
        countMinus();
    }

    private void findSentences() {
        for (String line : stringsToParse) {
            String[] sentencesFromLine = line.split("[.!?;]\\s|\"|<|>|\\n|\\r");
            for (String s : sentencesFromLine)
                checkPatterns(s);
        }
    }

    private void checkPatterns(String sentence) {
        for (Pattern p : patterns) {
            Matcher matcher = p.matcher(sentence);

            if (matcher.find()) {
                addOccurrences(sentence);
                break;
            }
        }
    }

    private void addOccurrences(String sentence) {
        stringParserLock.lock();
        result.add(sentence.trim());
        stringParserLock.unlock();
    }

    private void countMinus() {
        stringParserLock.lock();
        stringParserCounter.setCount(stringParserCounter.getCount() - 1);
        stringParserLock.unlock();
    }

    private void countPlus() {
        stringParserLock.lock();
        stringParserCounter.setCount(stringParserCounter.getCount() + 1);
        stringParserLock.unlock();
    }
}
