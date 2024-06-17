package org.kraaknet.anva.analyzer.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kraaknet.anva.analyzer.controller.model.FrequencyResponse;
import org.kraaknet.anva.analyzer.controller.model.TextRequest;
import org.kraaknet.anva.analyzer.controller.model.WordFrequency;
import org.kraaknet.anva.analyzer.controller.model.WordFrequencyRecord;
import org.kraaknet.anva.analyzer.controller.model.WordFrequencyRequest;
import org.kraaknet.anva.analyzer.service.WordFrequencyAnalyzerService;
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
        final var expected = FrequencyResponse.of(3232);
        when(service.calculateHighestFrequency(anyString())).thenReturn(expected.frequency());
        final FrequencyResponse result = controller.calculateHighestFrequency(new TextRequest(loremIpsumText));
        assertEquals(expected, result);
    }

    /**
     *  Just verify this passes things through to the service layer.
     */
    @Test
    void calculateFrequencyForWordTest() {
        final var expected = WordFrequencyRecord.builder()
                .word("moo")
                .frequency(594)
                .build();
        when(service.calculateFrequencyForWord(anyString(), anyString())).thenReturn(expected.frequency());
        final WordFrequency result = controller.calculateFrequencyForWord(WordFrequencyRequest.builder()
                .text(loremIpsumText)
                .word(expected.word())
                .build());
        assertEquals(expected, result);
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