package org.kraaknet.anva.analyzer.controller.model;

public interface WordFrequency {

    // Note - this deviates from the 'getter' style interface provided in the assignment.
    // The reason is that java Records use a slightly different style, and instead of having
    // delegations calling these, I've short-circuited it to match.
    String word();
    int frequency();

}
