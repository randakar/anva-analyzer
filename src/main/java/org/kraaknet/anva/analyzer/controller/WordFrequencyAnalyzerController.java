package org.kraaknet.anva.analyzer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kraaknet.anva.analyzer.controller.model.WordFrequency;
import org.kraaknet.anva.analyzer.service.WordFrequencyAnalyzerService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController("words")
@Validated
@Tag(name = "word analyzer", description = "Word analysis")
@RequestMapping(consumes = "application/json", produces = "application/json")
@RequiredArgsConstructor
public class WordFrequencyAnalyzerController  {

    private WordFrequencyAnalyzerService service;


    /**
     * POST /highest-frequency : Analyze a text.
     * Returns the highest frequency of any word occurring in the text (multiple words may have this frequency).
     *
     * @param text Text to analyze (required)
     * @return HTTP 200 result code with the highest frequency found in the text.
     */
    @Operation(
            operationId = "highest-frequency",
            summary = "Find the highest word frequency",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Text analyzed"),
            }
    )
    @PostMapping(value = "/highest-frequency")
    public int calculateHighestFrequency(@Parameter(name = "text", description = "Text to analyze", required = true)
                                         @Valid @RequestBody final String text) {
        return service.calculateHighestFrequency(text);
    }

    /**
     * POST /word-frequency: Analyze a text for occurrences of a particular word.
     * Returns the frequency for the specified word in the specified text.
     *
     * @param text Text to analyze (required)
     * @param word Word to look for in the text (required)
     * @return The highest frequency found in the text.
     */
    @Operation(
            operationId = "word-frequency",
            summary = "Find the highest word frequency",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Text analyzed"),
            }
    )
    @PostMapping(value = "/word-frequency")
    public int calculateFrequencyForWord(@Parameter(name = "text", description = "Text to analyze", required = true)
                                         @Valid @RequestBody final String text,
                                         @Parameter(name = "word", description = "Word to calculate frequency for", required = true)
                                         @Valid @RequestBody final String word) {
        return service.calculateFrequencyForWord(text, word);
    }

    /**
     * POST /word-frequency: Analyze a text for occurrences of a particular word.
     * Returns the frequency for the specified word in the specified text.
     *
     * @param text Text to analyze (required)
     * @param n Limit to top n results (required)
     * @return The top n words occurring the most in the text.
     */
    @Operation(
            operationId = "frequent-words",
            summary = "Find the highest word frequency",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Text analyzed"),
            }
    )
    @PostMapping(value = "/frequent-words")
    public List<WordFrequency> calculateMostFrequentNWords(@Parameter(name = "text", description = "Text to analyze", required = true)
                                                           @Valid @RequestBody final String text,
                                                           @Parameter(name = "n", description = "Max results limit", required = true)
                                                           @Valid @RequestBody final int n) {
        return service.calculateMostFrequentNWords(text, n);
    }
}
