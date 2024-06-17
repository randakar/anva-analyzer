package org.kraaknet.anva.analyzer.controller.model;

import jakarta.validation.constraints.NotNull;

public record TextRequest(@NotNull String text) {
}
