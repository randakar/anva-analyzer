package org.kraaknet.anva.analyzer.service.model;

import lombok.Builder;

@Builder
public record WordFrequencyRecord(String word, int frequency) implements WordFrequency {
}
