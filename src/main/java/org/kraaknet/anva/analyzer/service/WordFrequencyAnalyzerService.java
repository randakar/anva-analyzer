package org.kraaknet.anva.analyzer.service;

import org.kraaknet.anva.analyzer.controller.model.WordFrequency;
import org.kraaknet.anva.analyzer.controller.model.WordFrequencyRecord;
import org.kraaknet.anva.analyzer.service.analyzers.WordFrequencyAnalyzer;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.reducing;

@Service
public class WordFrequencyAnalyzerService implements WordFrequencyAnalyzer {

    private static final String WORD_EXPRESSION = "[a-zA-Z]+";
    public static final Pattern WORD_PATTERN = Pattern.compile(WORD_EXPRESSION);

    @Override
    public int calculateHighestFrequency(final String text) {
        final Map<String, Integer> frequencyMap = frequencyMapFor(text);
        return maxWordFrequencyIn(frequencyMap);
    }

    @Override
    public int calculateFrequencyForWord(final String text, final String word) {
        final long result = wordStreamOf(text)
                .filter(word::equalsIgnoreCase) // we could also implement a custom regular expression instead
                .count();
        return Math.toIntExact(result); // prevent integer overflows
    }

    @Override
    public List<WordFrequency> calculateMostFrequentNWords(final String text, final int n) {
        final Map<String, Integer> frequencyMap = frequencyMapFor(text);
        return frequencyMap.entrySet().stream()
                // Alternatively, instantiate Comparator for this
                .sorted((a, b) -> b.getValue() - a.getValue())
                .limit(n)
                .map(entry -> WordFrequencyRecord.builder()
                        .word(entry.getKey())
                        .frequency(entry.getValue())
                        .build())
                .map(WordFrequency.class::cast)
                .toList();
    }

    private static Map<String, Integer> frequencyMapFor(final String text) {
        return wordStreamOf(text)
                .collect(Collectors.groupingByConcurrent(word -> word,
                        reducing(0, e -> 1, Integer::sum)));
    }

    private static Stream<String> wordStreamOf(final String text) {
        final var matcher = WORD_PATTERN.matcher(text);
        return matcher.results()
                .map(MatchResult::group)
                .map(String::toLowerCase); // make this case-insensitive
    }

    private Integer maxWordFrequencyIn(final Map<String, Integer> frequencyMap) {
        return maxWordIn(frequencyMap)
                .map(maxWord -> frequencyMap.getOrDefault(maxWord, 0))
                .orElse(0);
    }

    private static Optional<String> maxWordIn(final Map<String, Integer> frequencyMap) {
        return frequencyMap.isEmpty() ? Optional.empty() :
                Optional.of(Collections.max(frequencyMap.entrySet(), Entry.comparingByValue()).getKey());
    }

}
