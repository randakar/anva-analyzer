package org.kraaknet.anva.analyzer.service.analyzers;

import org.kraaknet.anva.analyzer.service.model.WordFrequency;

import java.util.List;

public interface WordFrequencyAnalyzer {
    int calculateHighestFrequency(String text);
    int calculateFrequencyForWord(String text, String word);
    List<WordFrequency> calculateMostFrequentNWords(String text, int n);
}
