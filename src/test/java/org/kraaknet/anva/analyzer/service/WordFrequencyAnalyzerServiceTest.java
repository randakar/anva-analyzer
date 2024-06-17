package org.kraaknet.anva.analyzer.service;

import org.junit.jupiter.api.Test;
import org.kraaknet.anva.analyzer.service.model.WordFrequency;
import org.kraaknet.anva.analyzer.service.model.WordFrequencyRecord;

import java.util.List;
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
    void calculateMostFrequentNWordsTest() {
        final List<WordFrequency> expectedValues = List.of(
                frequency("ut", 13),
                frequency("ac", 10),
                frequency("id", 8),
                frequency("malesuada", 8),
                frequency("in", 7),
                frequency("tincidunt", 7),
                frequency("aliquam", 7),
                frequency("felis", 7),
                frequency("urna", 6),
                frequency("ipsum", 6)
        );

        final List<WordFrequency> result = service.calculateMostFrequentNWords(loremIpsumText, 10);
        assertEquals(expectedValues, result);
    }

    @Test
    void calculateMostFrequentNWordsShorterWordListTest() {
        final List<WordFrequency> expectedValues = List.of(
                frequency("foo", 3),
                frequency("bar", 1)
        );

        final List<WordFrequency> result = service.calculateMostFrequentNWords("foo foo, foo, bar", 10);
        assertEquals(expectedValues, result);
    }

    @Test
    void calculateMostFrequentNWordsNoWordListTest() {
        final List<WordFrequency> expectedValues = List.of();
        final List<WordFrequency> result = service.calculateMostFrequentNWords("", 10);
        assertEquals(expectedValues, result);
    }

    private static WordFrequency frequency(final String word, final int value) {
        return WordFrequencyRecord.builder()
                .word(word)
                .frequency(value)
                .build();
    }
}

