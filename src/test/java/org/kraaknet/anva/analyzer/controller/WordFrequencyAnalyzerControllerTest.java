package org.kraaknet.anva.analyzer.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kraaknet.anva.analyzer.service.WordFrequencyAnalyzerService;
import org.kraaknet.anva.analyzer.controller.model.WordFrequency;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.kraaknet.anva.analyzer.util.FileUtils.loadTextFromFile;
import static org.kraaknet.anva.analyzer.util.WordFrequencyUtils.frequency;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WordFrequencyAnalyzerControllerTest {

    @Mock
    private WordFrequencyAnalyzerService service;

    @InjectMocks
    private WordFrequencyAnalyzerController controller;

    private final String loremIpsumText = loadTextFromFile("lorem-ipsum.txt");


    /**
     *  Just verify this passes things through to the service layer.
     */
    @Test
    void calculateHighestFrequencyTest() {
        when(service.calculateHighestFrequency(anyString())).thenReturn(3232);
        final int result = controller.calculateHighestFrequency(loremIpsumText);
        assertEquals(3232, result);
    }

    /**
     *  Just verify this passes things through to the service layer.
     */
    @Test
    void calculateFrequencyForWordTest() {
        when(service.calculateFrequencyForWord(anyString(), anyString())).thenReturn(594);
        final int result = controller.calculateFrequencyForWord(loremIpsumText, "moo");
        assertEquals(594, result);
    }

    /**
     *  Just verify this passes things through to the service layer.
     */
    @Test
    void calculateMostFrequentNWordsTest() {
        final List<WordFrequency> expectedValues = List.of(
                frequency("foo", 3),
                frequency("bar", 1)
        );

        when(service.calculateMostFrequentNWords(anyString(), anyInt())).thenReturn(expectedValues);
        final List<WordFrequency> result = controller.calculateMostFrequentNWords(loremIpsumText, 231);
        assertEquals(expectedValues, result);
    }
}