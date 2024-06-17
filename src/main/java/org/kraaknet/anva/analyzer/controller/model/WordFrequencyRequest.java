package org.kraaknet.anva.analyzer.controller.model;

import lombok.Builder;

@Builder
public record WordFrequencyRequest(String text, String word) {
}
