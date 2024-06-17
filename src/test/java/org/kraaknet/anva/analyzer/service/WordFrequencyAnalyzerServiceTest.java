package org.kraaknet.anva.analyzer.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.kraaknet.anva.analyzer.service.util.FileUtilities.loadTextFromFile;

class WordFrequencyAnalyzerServiceTest {

    private final String loremIpsumText = loadTextFromFile("lorem-ipsum.txt");
    private final WordFrequencyAnalyzerService service = new WordFrequencyAnalyzerService();

    @Test
    void calculateHighestFrequencyTest() {
        final int result = service.calculateHighestFrequency(loremIpsumText);
        assertEquals(13, result); // The word "ut" in the lorem ipsum text occurs 13 times, in different casing
    }

    @Test
    void calculateHighestFrequencyTestEmptyText() {
        final int result = service.calculateHighestFrequency("");
        assertEquals(0, result); // No text means 0 is the highest frequency.
    }


    @Test
    void calculateFrequencyForWord() {
    }

    @Test
    void calculateMostFrequentNWords() {
    }
}

