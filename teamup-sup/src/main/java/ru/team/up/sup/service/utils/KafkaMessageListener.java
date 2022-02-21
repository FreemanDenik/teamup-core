package ru.team.up.sup.service.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.team.up.dto.AppModuleNameDto;
import ru.team.up.sup.repository.SupRepository;
import ru.team.up.dto.SupParameterDto;


@Slf4j
@Service
public class KafkaMessageListener {
    private SupRepository supRepository;
    @Value(value = "${sup.kafka.system.name}")
    private AppModuleNameDto systemName;

    @Autowired
    public KafkaMessageListener(SupRepository supRepository) {
        this.supRepository = supRepository;
    }

    @KafkaListener(topics = "${sup.kafka.topic.name}", containerFactory = "kafkaListenerContainerFactory")
    public void listener(SupParameterDto parameterDto) {

        if (parameterDto == null) {
            log.debug("KafkaListener: The parameter value is null.");
            // Проверка подходит ли параметр по имени системы
        } else if (parameterDto.getSystemName().equals(systemName)) {
            log.debug("KafkaListener, message: {}", parameterDto);
            supRepository.add(parameterDto);
        }
    }
}
