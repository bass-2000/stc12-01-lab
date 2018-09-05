package ru.innopolis.stc12.lab.wordFinder.Utils;

import java.util.regex.Pattern;

public class PatternsArray {
    private Pattern[] patterns;

    public PatternsArray(String[] words) {
        words = toLowerCase(words);

        Pattern[] patterns = new Pattern[words.length];
        int i = 0;
        for (String w : words) {
            Pattern pattern = Pattern.compile(
                    "(\\s(" + w + ")\\s)"
                            + "|" +
                            "(.(" + w + ")\\s)"
                            + "|" +
                            "(\\s(" + w + ").)"
                            + "|" +
                            "(\\((" + w + ")\\))"
                            + "|" +
                            "(\"(" + w + ")\")"
            );
            patterns[i] = pattern;
            i++;
        }
        this.patterns = patterns;

    }

    private String[] toLowerCase(String[] words) {
        int i = 0;
        String[] wordsToLowerCase = new String[words.length];
        for (String w : words) {
            w = w.toLowerCase();
            wordsToLowerCase[i] = w;
            i++;
        }
        return wordsToLowerCase;
    }

    public Pattern[] getPatterns() {
        return patterns;
    }
}