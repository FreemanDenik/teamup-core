package ru.team.up.sup.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.team.up.dto.AppModuleNameDto;

@Slf4j
@Service
public class KafkaSupServiceImpl implements KafkaSupService {

    @Value(value = "${sup.kafka.system.name}")
    private AppModuleNameDto systemName;
    @Value(value = "${sup.kafka.init.topic.name}")
    private String initTopic;
    private final KafkaTemplate<String, AppModuleNameDto> kafkaTemplate;

    @Autowired
    public KafkaSupServiceImpl(KafkaTemplate<String, AppModuleNameDto> kafkaModuleNameTemplate) {
        this.kafkaTemplate = kafkaModuleNameTemplate;
    }

    @Override
    public void getAllModuleParameters() {
        kafkaTemplate.send(initTopic, systemName);
    }
}