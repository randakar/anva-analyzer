package org.kraaknet.anva.analyzer.service;

import org.junit.jupiter.api.Test;

import java.util.Map;

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
    void calculateHighestFrequencyTestEmptyTextTest() {
        final int result = service.calculateHighestFrequency("");
        assertEquals(0, result); // No text means 0 is the highest frequency.
    }


    @Test
    void calculateFrequencyForWordTest() {
        final Map<String, Integer> testCases = Map.of("ac", 10,
                "volutpat", 4,
                "lorem", 4, // Case sensitivity test
                "califragilisticexpialidocious", 0 // not found test
                );
        testCases.forEach(((word, count) ->
                assertEquals(count, service.calculateFrequencyForWord(loremIpsumText, word))));
    }

    @Test
    void calculateMostFrequentNWords() {
    }
}

