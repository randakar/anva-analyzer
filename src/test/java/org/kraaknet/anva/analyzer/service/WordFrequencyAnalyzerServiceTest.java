package org.kraaknet.anva.analyzer.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.kraaknet.anva.analyzer.service.util.FileUtilities.loadTextFromFile;

class WordFrequencyAnalyzerServiceTest {

    private final String loremIpsumText = loadTextFromFile("lorem-ipsum.txt");

    @Test
    void calculateHighestFrequencyTest() {
        assertNotNull(loremIpsumText);
    }

    @Test
    void calculateFrequencyForWord() {
    }

    @Test
    void calculateMostFrequentNWords() {
    }
}

