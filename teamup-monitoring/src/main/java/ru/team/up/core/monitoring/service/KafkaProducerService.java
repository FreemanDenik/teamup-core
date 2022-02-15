package ru.team.up.core.monitoring.service;

public interface KafkaProducerService {
    void send(String content);
}
