package org.kraaknet.anva.analyzer.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kraaknet.anva.analyzer.service.WordFrequencyAnalyzerService;
import org.kraaknet.anva.analyzer.service.model.WordFrequency;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
public class WordFrequencyAnalyzerController  {

    private WordFrequencyAnalyzerService service;

    public int calculateHighestFrequency(final String text) {
        return service.calculateHighestFrequency(text);
    }

    public int calculateFrequencyForWord(final String text, final String word) {
        return service.calculateFrequencyForWord(text, word);
    }

    public List<WordFrequency> calculateMostFrequentNWords(final String text, final int n) {
        return service.calculateMostFrequentNWords(text, n);
    }
}
