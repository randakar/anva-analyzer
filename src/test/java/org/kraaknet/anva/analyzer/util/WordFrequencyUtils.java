package org.kraaknet.anva.analyzer.util;

import lombok.experimental.UtilityClass;
import org.kraaknet.anva.analyzer.service.model.WordFrequency;
import org.kraaknet.anva.analyzer.service.model.WordFrequencyRecord;

@UtilityClass
public class WordFrequencyUtils {

    public static WordFrequency frequency(final String word, final int value) {
        return WordFrequencyRecord.builder()
                .word(word)
                .frequency(value)
                .build();
    }
}
