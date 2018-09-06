package ru.innopolis.stc12.lab.wordFinder;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class ReaderWriter {
    final String REGEX_WORDS = "[,;:.!?'_\\-\"\\s]+";
    final String REGEX_SENTENCES = "^\\s+[A-Za-z,;'\"\\s]+[.?!]$";

    public ReaderWriter() {
    }

    public boolean isEmpty(Object[] objects, String[] descriptions) {
        boolean isEmpty = false;
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] == null) {
                isEmpty = true;
            }
        }
        return isEmpty;
    }

    public boolean isEmpty(Object obj, String description) {
        return (obj == null);
    }

    public String[] readFromBigFile(String fileName, String[] searchWords) {
        String[] readFromFileSentences;
        String[] tempReady;
        String[] readySentences = new String[0];
        if (!isEmpty(fileName, "файл")) {
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                ArrayList<String> tempArray = new ArrayList<>();
                ArrayList<String> tempReadySentences = new ArrayList<>();
                StringBuffer sb = new StringBuffer();
                int c;
                while ((c = br.read()) != -1) { // чтение посимвольно
                    if (tempArray.size() < 10_000) { //для того чтобы ArrayList не переполнялся
                        char ch = (char) c;
                        if (ch != '.' && ch != '!' && ch != '?') {
                            sb.append(ch);
                        } else {
                            tempArray.add(sb.toString());
                            sb = new StringBuffer();
                        }
                    } else {
                        readFromFileSentences = tempArray.toArray(new String[tempArray.size()]);
                        tempReady = wordSearch(readFromFileSentences, searchWords);
                        for (String s : tempReady) {
                            tempReadySentences.add(s);
                        }
                        tempArray = new ArrayList<>();
                    }
                }
                readFromFileSentences = tempArray.toArray(new String[tempArray.size()]);
                tempReady = wordSearch(readFromFileSentences, searchWords);
                for (String s : tempReady) {
                    tempReadySentences.add(s);
                }
                readySentences = tempReadySentences.toArray(new String[tempReadySentences.size()]);
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
        return readySentences;
    }

    public String readFrom(String fileName) {
        if (fileName.contains("http") || fileName.contains("ftp://")) return readFromURL(fileName);
        else return readFromLocalFile(fileName);
    }

    public String readFromLocalFile(String fileName) {
        String readFromFile = "";
        if (!isEmpty(fileName, "файл")) {
            {
                byte[] encoded = new byte[0];
                try {
                    encoded = Files.readAllBytes(Paths.get(fileName));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    readFromFile = new String(encoded, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return readFromFile;
    }

    public String readFromURL(String url) {
        String result = "";
        try {
            URLConnection uc = new URL(url).openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            String inputLine;
            StringBuilder sb = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
            in.close();
            result = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("parsing url " + url + " : " + result);
        return result;
    }

    public String[] writeToSentences(String input) {
        String[] sentences;
        if (isEmpty(input, "текст")) {
            sentences = new String[0];
        } else {
            Pattern pattern = Pattern.compile(REGEX_SENTENCES);
            sentences = pattern.split(input);
        }
        return sentences;
    }

    private String[] splitIntoWords(String inputSentence) {
        String[] words;
        if (isEmpty(inputSentence, "предложение")) {
            words = new String[0];
        } else {
            Pattern pattern = Pattern.compile(REGEX_WORDS);
            words = pattern.split(inputSentence);
        }
        return words;
    }

    public synchronized void writeToResult(String[] resultSentences, String result) {
        Object[] args = {resultSentences, result};
        String[] descriptions = {"массив готовых предложений", "файл"};
        if (!isEmpty(args, descriptions)) {
            String[] sentences = resultSentences;
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(result, true))) {
                for (String sentence : sentences) {
                    bw.write(sentence);
                    bw.append("\r\n");
                }
                bw.flush();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    public String[] wordSearch(String[] allSentences, String[] searchWords) {
        String[] resultSentences;
        Object[] args = {allSentences, searchWords};
        String[] descriptions = {"массив исходных предложений", "массив искомых слов"};
        if (!isEmpty(args, descriptions)) {
            ArrayList<String> tempArray = new ArrayList<>();
            for (int i = 0; i < allSentences.length; i++) {
                if (searchInOneSentence(allSentences[i], searchWords)) {
                    tempArray.add(allSentences[i]);
                }
            }
            resultSentences = tempArray.toArray(new String[tempArray.size()]);
        } else {
            resultSentences = new String[0];
        }
        return resultSentences;
    }

    private boolean searchInOneSentence(String oneSentence, String[] searchWords) {
        String[] words = splitIntoWords(oneSentence);
        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j < searchWords.length; j++) {
                if ((words[i].toLowerCase()).equals(searchWords[j].toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }

}