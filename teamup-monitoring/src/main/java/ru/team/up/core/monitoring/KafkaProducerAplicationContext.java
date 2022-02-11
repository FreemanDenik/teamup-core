package ru.team.up.core.monitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaProducerAplicationContext {
    public static void main(String[] args) {
        SpringApplication.run(KafkaProducerAplicationContext.class, args);
    }
}