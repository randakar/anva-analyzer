package org.kraaknet.anva.analyzer.controller.model;

import lombok.Builder;

@Builder
public record WordFrequencyRecord(String word, int frequency) implements WordFrequency {
}
