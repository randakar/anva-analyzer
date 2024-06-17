package org.kraaknet.anva.analyzer;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SpringExtension.class)
class AnvaAnalyzerApplicationTests {

    private static final HttpHeaders httpHeaders = new HttpHeaders() {{
        set(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE);
    }};


    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void testContextLoads() {
        // No errors so far, so application started successfully
        assertTrue(TRUE);
    }

    @Test
    void givenNotExistingEndpointTest() {
        final var entity = new HttpEntity<>(httpHeaders);
        final var response = testRestTemplate
                .exchange("/no-such-endpoint", GET, entity, Void.class);
        assertNotNull(response);
        assertTrue(response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(404)));
    }


}
