package org.kraaknet.anva.analyzer.controller.model;

import lombok.Builder;

import jakarta.validation.constraints.NotNull;

@Builder
public record TopWordFrequencyRequest(@NotNull String text, @NotNull Integer limit) {
}
