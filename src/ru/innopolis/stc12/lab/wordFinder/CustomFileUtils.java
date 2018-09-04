package ru.innopolis.stc12.lab.wordFinder;

import java.io.*;

public class CustomFileUtils {

    public String readFromFile(String fileName) {
        String result = "";
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            StringBuffer stringBuffer = new StringBuffer();
            int c;
            while ((c = bufferedReader.read()) != -1) {
                stringBuffer.append((char) c);
            }
            result = stringBuffer.toString();
        } catch (IOException ex) {
        }
        return result;
    }

    public void writeResultToFile(String[] result, String fileName) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, true))) {
            for (String sentence : result) {
                bufferedWriter.write(sentence);
                bufferedWriter.append("\r\n");
            }
            bufferedWriter.flush();
        } catch (IOException e) {
        }
    }

}
