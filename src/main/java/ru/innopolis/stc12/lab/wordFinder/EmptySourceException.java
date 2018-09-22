package ru.innopolis.stc12.lab.wordFinder;

import org.apache.log4j.Logger;

public class EmptySourceException extends Exception {
    private String source;
    final static Logger logger = Logger.getLogger(EmptySourceException.class);
    public EmptySourceException(String source) {
        logger.error("Empty source: " + source);
    }
}
