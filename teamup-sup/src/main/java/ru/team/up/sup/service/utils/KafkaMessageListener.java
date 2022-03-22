package ru.team.up.sup.service.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.team.up.dto.AppModuleNameDto;
import ru.team.up.sup.repository.SupRepository;
import ru.team.up.dto.SupParameterDto;
import ru.team.up.sup.service.SupService;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class KafkaMessageListener {

    private SupRepository supRepository;
    private SupService supService;

    @Value(value = "${sup.kafka.system.name}")
    private AppModuleNameDto systemName;

    private KafkaTemplate<String, AppModuleNameDto> kafkaTemplate;

    @Autowired
    public KafkaMessageListener(SupRepository supRepository, SupService supService,
                                @Qualifier("producerKafkaTemplate") KafkaTemplate<String, AppModuleNameDto> kafkaTemplate) {
        this.supService = supService;
        this.supRepository = supRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostConstruct
    public void init() {
        kafkaTemplate.send("initialization", systemName);
    }

    @KafkaListener(topics = "${sup.kafka.topic.name}", containerFactory = "kafkaListenerContainerFactory")
    public void listener(SupParameterDto parameterDto) {
        log.debug("KafkaListener, message: {}", parameterDto);
        supRepository.add(parameterDto);
        System.out.println("Updated parameter " + parameterDto.getParameterName());
        supService.getListParameters().stream().forEach(p ->
                System.out.println(p.getParameterName() + " " + p.getParameterValue()));
    }
}
