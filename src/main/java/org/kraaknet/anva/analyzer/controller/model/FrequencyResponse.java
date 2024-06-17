package org.kraaknet.anva.analyzer.controller.model;

public record FrequencyResponse(int frequency) {

    public static FrequencyResponse of(int frequency) {
        return new FrequencyResponse(frequency);
    }
}
