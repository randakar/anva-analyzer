package org.kraaknet.anva.analyzer.controller.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record WordFrequencyRecord(@NotNull String word, int frequency) implements WordFrequency {

}
