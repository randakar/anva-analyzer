package org.kraaknet.anva.analyzer.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@UtilityClass
public class FileUtils {

    @SneakyThrows
    public static String loadTextFromFile(final String fileName) {
        final var classLoader = FileUtils.class.getClassLoader();
        try(final InputStream stream = classLoader.getResourceAsStream(fileName)) {
            assertNotNull(stream);
            return new String(stream.readAllBytes());
        }
    }
}
