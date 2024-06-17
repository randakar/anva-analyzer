package org.kraaknet.anva.analyzer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kraaknet.anva.analyzer.controller.model.FrequencyResponse;
import org.kraaknet.anva.analyzer.controller.model.TextRequest;
import org.kraaknet.anva.analyzer.controller.model.WordFrequency;
import org.kraaknet.anva.analyzer.controller.model.WordFrequencyRecord;
import org.kraaknet.anva.analyzer.controller.model.WordFrequencyRequest;
import org.kraaknet.anva.analyzer.service.WordFrequencyAnalyzerService;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@RestController("words")
@Validated
@Tag(name = "word analyzer", description = "Word analysis")
@RequestMapping(consumes = "application/json", produces = "application/json")
@RequiredArgsConstructor
public class WordFrequencyAnalyzerController  {

    @NonNull
    private WordFrequencyAnalyzerService service;


    /**
     * POST /highest-frequency : Analyze a text.
     * Returns the highest frequency of any word occurring in the text (multiple words may have this frequency).
     *
     * @param request Request text to analyze (required)
     * @return HTTP 200 result code with the highest frequency found in the text.
     */
    @Operation(
            operationId = "highest-frequency",
            summary = "Find the highest word frequency",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Text analyzed"),
            }
    )
    @PostMapping(value = "/highest-frequency", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public FrequencyResponse calculateHighestFrequency(@Parameter(name = "textRequest", description = "Text to analyze", required = true)
                                         @NotNull @Valid @RequestBody final TextRequest request) {
        int frequency = service.calculateHighestFrequency(request.text());
        return FrequencyResponse.of(frequency);
    }

    /**
     * POST /word-frequency: Analyze a text for occurrences of a particular word.
     * Returns the frequency for the specified word in the specified text.
     *
     * @param request Text to analyze and word to look for (required)
     * @return The highest frequency found in the text.
     */
    @Operation(
            operationId = "word-frequency",
            summary = "Find the highest word frequency",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Text analyzed"),
            }
    )
    @PostMapping(value = "/word-frequency", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public WordFrequency calculateFrequencyForWord(@Parameter(name = "wordFrequencyRequest", description = "Text with word to analyze", required = true)
                                                       @NotNull @Valid @RequestBody final WordFrequencyRequest request) {
        int frequency = service.calculateFrequencyForWord(request.text(), request.word());
        return WordFrequencyRecord.builder()
                .word(request.word())
                .frequency(frequency)
                .build();
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
    @PostMapping(value = "/frequent-words", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<WordFrequency> calculateMostFrequentNWords(@Parameter(name = "text", description = "Text to analyze", required = true)
                                                           @Valid @RequestParam final String text,
                                                           @Parameter(name = "n", description = "Max results limit", required = true)
                                                           @Valid @RequestParam final int n) {
        return service.calculateMostFrequentNWords(text, n);
    }
}
