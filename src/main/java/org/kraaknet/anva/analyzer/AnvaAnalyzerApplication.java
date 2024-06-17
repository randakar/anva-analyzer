package org.kraaknet.anva.analyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class AnvaAnalyzerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnvaAnalyzerApplication.class, args);
    }

}
