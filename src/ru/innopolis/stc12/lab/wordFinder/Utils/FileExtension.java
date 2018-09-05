package ru.innopolis.stc12.lab.wordFinder.Utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class FileExtension {
    private String source;

    public FileExtension(String source) {
        this.source = source;
    }

    public Scanner getFileContent() throws IOException {
        String fileExtension = source.substring(0, 4);
        switch (fileExtension) {
            case "ftp:":
                return new Scanner(new URL(source).openStream());
            case "http":
                return new Scanner(new URL(source).openStream());
            default:
                return new Scanner(new File(source));
        }
    }
}
