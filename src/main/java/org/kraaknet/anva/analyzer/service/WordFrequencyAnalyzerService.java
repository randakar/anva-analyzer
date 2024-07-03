package org.kraaknet.anva.analyzer.service;

import org.kraaknet.anva.analyzer.controller.model.WordFrequency;
import org.kraaknet.anva.analyzer.controller.model.WordFrequencyRecord;
import org.kraaknet.anva.analyzer.service.analyzers.WordFrequencyAnalyzer;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
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
        // Highest number of occurrences first,
        final Comparator<Entry<String, Integer>> compareByFrequency = Entry.comparingByValue();
        // and then alphabetically by word (stored in the key).
        final Comparator<Entry<String, Integer>> compareByFrequencyThenAlphabetically = compareByFrequency
                .thenComparing(Entry.comparingByKey());
        return frequencyMap.entrySet().stream()
                .sorted(compareByFrequencyThenAlphabetically)
                .limit(n)
                .map(entry -> new WordFrequencyRecord(entry.getKey(), entry.getValue()))
                .map(WordFrequency.class::cast)
                .toList();
    }

    private static Map<String, Integer> frequencyMapFor(final String text) {
        return wordStreamOf(text)
                .collect(Collectors.groupingByConcurrent(word -> word,
                        reducing(0, e -> 1, Integer::sum)));
    }

    private static Stream<String> wordStreamOf(final String text) {
        final Matcher matcher = WORD_PATTERN.matcher(text);
        return matcher.results()
                .map(MatchResult::group)
                .map(String::toLowerCase); // make this case-insensitive
    }

    private int maxWordFrequencyIn(final Map<String, Integer> frequencyMap) {
        return frequencyMap.isEmpty() ? 0 : Collections.max(frequencyMap.values(), Integer::compareTo);
    }

}
