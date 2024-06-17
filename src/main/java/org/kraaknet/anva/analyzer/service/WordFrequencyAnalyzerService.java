package org.kraaknet.anva.analyzer.service;

import org.kraaknet.anva.analyzer.service.analyzers.WordFrequencyAnalyzer;
import org.kraaknet.anva.analyzer.service.model.WordFrequency;

import java.util.List;

public class WordFrequencyAnalyzerService implements WordFrequencyAnalyzer {

    @Override
    public int calculateHighestFrequency(final String text) {
        return 0;
    }

    @Override
    public int calculateFrequencyForWord(final String text, final String word) {
        return 0;
    }

    @Override
    public List<WordFrequency> calculateMostFrequentNWords(final String text, int n) {
        return List.of();
    }
}
