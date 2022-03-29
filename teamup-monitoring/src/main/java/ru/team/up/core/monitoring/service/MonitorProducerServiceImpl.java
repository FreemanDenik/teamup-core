package ru.team.up.core.monitoring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.team.up.dto.ReportDto;

@Slf4j
@Service
public class MonitorProducerServiceImpl implements MonitorProducerService {

    private KafkaTemplate kafkaTemplate;

    @Value("${kafka.topic.name}")
    private String topic;

    public MonitorProducerServiceImpl(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(ReportDto content) {
        log.debug(topic + " ------------ " + content);
        kafkaTemplate.send(topic, content);
    }
}
