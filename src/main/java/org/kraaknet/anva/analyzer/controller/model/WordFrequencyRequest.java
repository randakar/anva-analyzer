package org.kraaknet.anva.analyzer.controller.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import jakarta.validation.constraints.NotNull;

@Builder
public record WordFrequencyRequest(@NotNull String text, @NotNull @NotEmpty String word) {
}
