package org.kraaknet.anva.analyzer;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kraaknet.anva.analyzer.controller.model.FrequencyResponse;
import org.kraaknet.anva.analyzer.controller.model.WordFrequency;
import org.kraaknet.anva.analyzer.controller.model.WordFrequencyRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;

import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.kraaknet.anva.analyzer.util.FileUtils.loadTextFromFile;
import static org.kraaknet.anva.analyzer.util.WordFrequencyUtils.frequency;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SpringExtension.class)
class AnvaAnalyzerApplicationTests {

    private static final HttpHeaders HTTP_HEADERS = new HttpHeaders() {{
        set(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE);
    }};

    private final String TEST_TEXT = loadTextFromFile("lorem-ipsum.txt");

    @Autowired
    @SuppressWarnings("unused")
    private TestRestTemplate testRestTemplate;

    @Test
    void testContextLoads() {
        // No errors so far, so application started successfully
        assertTrue(TRUE);
    }

    @Test
    void notExistingEndpointTest() {
        final var entity = new HttpEntity<>(HTTP_HEADERS);
        final var response = testRestTemplate
                .exchange("/no-such-endpoint", GET, entity, Void.class);
        assertNotNull(response);
        assertTrue(response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(404)));
    }

    @Test
    void highestFrequencyEndpointTest() {
        final var response = callHighestFrequency(TEST_TEXT);
        assertTrue(response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(200)));
        assertEquals(FrequencyResponse.of(13), response.getBody());
    }

    @Test
    void highestFrequencyEndpointEmptyTextTest() {
        final var response = callHighestFrequency("");
        assertTrue(response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(200)));
        assertEquals(FrequencyResponse.of(0), response.getBody());
    }

    @Test
    void calculateFrequencyForWordTest() {
        final Map<String, Integer> testCases = Map.of("ac", 10,
                "volutpat", 4,
                "lorem", 4, // Case sensitivity test
                "califragilisticexpialidocious", 0 // not found test
        );
        testCases.forEach(((word, count) -> {
            final var expected = WordFrequencyRecord.builder()
                    .word(word)
                    .frequency(count)
                    .build();

            final ResponseEntity<WordFrequency> response = callCalculateFrequencyForWord(TEST_TEXT, word);
            assertTrue(response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(200)));
            assertEquals(expected, response.getBody());
        }));
    }

    @Test
    void calculateMostFrequentNWordsTest() {
        final List<WordFrequency> expectedValues = List.of(
                frequency("ut", 13),
                frequency("ac", 10),
                frequency("id", 8),
                frequency("malesuada", 8),
                frequency("in", 7),
                frequency("tincidunt", 7),
                frequency("aliquam", 7),
                frequency("felis", 7),
                frequency("urna", 6),
                frequency("ipsum", 6)
        );

        final ResponseEntity<List<WordFrequency>> response = callCalculateMostFrequentNWords(TEST_TEXT, 10);
        assertTrue(response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(200)));
        assertEquals(expectedValues, response.getBody());
    }

    @Test
    void calculateMostFrequentNWordsShorterWordListTest() {
        final List<WordFrequency> expectedValues = List.of(
                frequency("foo", 3),
                frequency("bar", 1),
                frequency("boo", 1),
                frequency("fa", 1)
        );

        final ResponseEntity<List<WordFrequency>> response = callCalculateMostFrequentNWords("boo fa foo bar foo, foo $^&@", 10);
        assertTrue(response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(200)));
        assertEquals(expectedValues, response.getBody());
    }

    @Test
    void calculateMostFrequentNWordsNoWordListTest() {
        final List<WordFrequency> expectedValues = List.of();
        final ResponseEntity<List<WordFrequency>> response = callCalculateMostFrequentNWords("", 10);
        assertTrue(response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(200)));
        assertEquals(expectedValues, response.getBody());
    }

    private ResponseEntity<FrequencyResponse> callHighestFrequency(final String text) {
        record RequestRecord(String text) {}
        final var request = new HttpEntity<RequestRecord>(new RequestRecord(text), HTTP_HEADERS);
        return testRestTemplate.exchange("/highest-frequency", POST, request, FrequencyResponse.class);
    }

    private ResponseEntity<WordFrequency> callCalculateFrequencyForWord(final String text, final String word) {
        record WordFrequencyRequestRecord(String text, String word) {}
        final var request = new HttpEntity<WordFrequencyRequestRecord>(new WordFrequencyRequestRecord(text, word), HTTP_HEADERS);
        return testRestTemplate.exchange("/word-frequency", POST, request, WordFrequency.class);
    }

    private ResponseEntity<List<WordFrequency>> callCalculateMostFrequentNWords(final String text, final int limit) {
        record TopWordFrequencyRequestRecord(String text, int limit) {}
        final ParameterizedTypeReference<List<WordFrequency>> typeReference = new ParameterizedTypeReference<>() {};
        final var request = new HttpEntity<TopWordFrequencyRequestRecord>(new TopWordFrequencyRequestRecord(text, limit), HTTP_HEADERS);
        return testRestTemplate.exchange("/frequent-words", POST, request, typeReference);
    }



}
