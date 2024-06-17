package org.kraaknet.anva.analyzer.service;

import org.kraaknet.anva.analyzer.service.analyzers.WordFrequencyAnalyzer;
import org.kraaknet.anva.analyzer.service.model.WordFrequency;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;

public class WordFrequencyAnalyzerService implements WordFrequencyAnalyzer {

    private static final String WORD_EXPRESSION = "[a-zA-Z]+";
    public static final Pattern WORD_PATTERN = Pattern.compile(WORD_EXPRESSION);

    @Override
    public int calculateHighestFrequency(final String text) {
        final var matcher = WORD_PATTERN.matcher(text);
        final Map<String, Long> frequencyMap = matcher.results()
                .map(MatchResult::group)
                .collect(Collectors.groupingByConcurrent(word -> word, counting()));
        return frequencyMap.isEmpty() ? 0 : extractMaxFrequency(frequencyMap).intValue();
    }

    private Long extractMaxFrequency(final Map<String, Long> frequencyMap) {
        final String maxWord = Collections.max(frequencyMap.entrySet(), Map.Entry.comparingByValue()).getKey();
        return frequencyMap.getOrDefault(maxWord, 0L);
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
