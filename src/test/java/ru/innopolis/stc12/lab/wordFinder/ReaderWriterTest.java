package ru.innopolis.stc12.lab.wordFinder;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReaderWriterTest {
    final static Logger logger = Logger.getLogger(ReaderWriterTest.class);
    private final static String testFileName = "testfilename.txt";
    private ReaderWriter readerWriter;

    @BeforeAll
    static void setUp() {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(testFileName), "utf-8"))) {
            writer.write("something");
            logger.info("\u001B[34m" + "File " + testFileName + " created successfully" + "\u001B[0m");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @AfterAll
    static void tearDown() {
        try {
            Files.deleteIfExists(Paths.get(testFileName));
            logger.info("\u001B[34m" + "File " + testFileName + " was deleted successfully" + "\u001B[0m");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @BeforeEach
    void setUpClass() {
        readerWriter = new ReaderWriter();
        logger.info("Test starting");
    }

    @Test
    void readFromLocalFile() {
        String result = readerWriter.readFromLocalFile(testFileName);
        assertEquals("something", result);
        logger.info("Expected string was found in a file " + testFileName);
    }

    @Test
    void readFromURL() {
        String result = readerWriter.readFromURL("http://generator.lorem-ipsum.info/");
        String expected = "Most of its text is made up from sections";
        assertTrue(result.contains(expected));
        logger.info("Expected string was found " + expected);
    }

    @Test
    void readFromBigFile() {
        String[] words = {"ipsum", "donec"};
        String[] strings = readerWriter.readFromBigFile("resources", words);
        String expected = "Lorem ipsum dolor sit amet, consectetur adipiscing elit";
        assertEquals(expected, strings[0]);
        logger.info("Expected string was found " + expected);
    }

    @Test
    void wordSearch() {
        String[] sentences = {"Lorem Ipsum является стандартной \"рыбой\" для текстов на латинице с начала XVI века.", "В то время некий безымянный печатник создал большую коллекцию размеров и форм шрифтов, используя Lorem Ipsum для распечатки образцов.", "Lorem Ipsum не только успешно пережил без заметных изменений пять веков, но и перешагнул в электронный дизайн.", "Его популяризации в новое время послужили публикация листов Letraset с образцами Lorem Ipsum в 60-х годах и, в более недавнее время, программы электронной вёрстки типа Aldus PageMaker, в шаблонах которых используется Lorem Ipsum."};
        String[] words = {"lorem", "печатник"};
        String[] result = readerWriter.wordSearch(sentences, words);
        assertEquals(sentences[0], result[0]);
        logger.info("Expected string was found: " + result[0]);
    }

    @Test
    void writeToSentences() {
        String input = "London is the Capital of Great Britan.\n Nothing to do here.";
        String[] result = readerWriter.writeToSentences(input);
        String expected = "London is the Capital of Great Britan.";
        assertEquals(expected, result[0]);
        logger.info("Expected string was found: " + result[0]);
    }

    @Test
    void splitIntoWords() {
        String input = "London is the Capital of Great Britan.";
        String[] result = readerWriter.splitIntoWords(input);
        String[] expected = {"London", "is", "the", "Capital", "of", "Great", "Britan"};
        for (int i = 0; i < 7; i++) {
            assertEquals(expected[i], result[i]);
            logger.info("Expected string was found: " + result[i]);
        }

    }

    @AfterEach
    void eachTestTearDown() {
        logger.info("Test ended");
    }
}