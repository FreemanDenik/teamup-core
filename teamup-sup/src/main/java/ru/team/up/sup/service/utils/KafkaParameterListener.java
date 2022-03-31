package ru.team.up.sup.service.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.team.up.dto.AppModuleNameDto;
import ru.team.up.dto.ListSupParameterDto;
import ru.team.up.dto.SupParameterDto;
import ru.team.up.sup.service.ParameterService;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class KafkaParameterListener {

    private final ParameterService parameterService;

    @Value(value = "${sup.kafka.system.name}")
    private AppModuleNameDto systemName;

    private final KafkaTemplate<String, AppModuleNameDto> kafkaTemplate;

    @Autowired
    public KafkaParameterListener(KafkaTemplate<String, AppModuleNameDto> kafkaModuleNameTemplate,
                                  ParameterService parameterService) {
        this.parameterService = parameterService;
        this.kafkaTemplate = kafkaModuleNameTemplate;
    }

    @PostConstruct
    public void init() {
        kafkaTemplate.send("initialization", systemName);
    }

    @KafkaListener(topics = "${sup.kafka.topic.name}", containerFactory = "listDtoKafkaContainerFactory")
    public void listener(ListSupParameterDto listParameterDto) {
        for (SupParameterDto<?> parameterDto : listParameterDto.getList()) {
            log.debug("Получен параметр {}", parameterDto.getParameterName());
            parameterService.addParam(parameterDto);
        }
    }
}
