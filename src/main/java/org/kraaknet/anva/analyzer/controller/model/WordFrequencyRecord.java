package org.kraaknet.anva.analyzer.controller.model;

import lombok.Builder;

import jakarta.validation.constraints.NotNull;

@Builder
public record WordFrequencyRecord(@NotNull String word, int frequency) implements WordFrequency {
}
