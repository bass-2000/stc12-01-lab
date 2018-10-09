package ru.innopolis.stc12.lab.wordFinder;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SingleResourceParser {
    private Pattern pattern;
    private Logger logger = Logger.getLogger(SingleResourceParser.class);

    SingleResourceParser() {
        this.pattern = Pattern.compile("[.!?]");
    }

    public String getSentence(String str, String[] words) {
        StringBuilder sentenceList = new StringBuilder();
        StringBuilder buffer = new StringBuilder();
        try (BufferedReader bf = new BufferedReader(new FileReader(str))) {
            while (bf.ready()) {
                int ch = bf.read();
                buffer.append((char) ch);
                if (isSentenceEnd((char) ch)) {
                    for (String word : words) {
                        if ((word != null) && (buffer.toString().contains(word))) {
                            sentenceList.append(buffer.toString().trim()).append("\n");
                        }
                    }
                    buffer.delete(0, buffer.length());
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return sentenceList.toString();
    }

    private boolean isSentenceEnd(char ch) {
        Matcher m = pattern.matcher(String.valueOf(ch));
        return m.find();
    }
}