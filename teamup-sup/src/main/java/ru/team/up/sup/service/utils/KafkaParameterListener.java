package ru.team.up.sup.service.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.team.up.dto.ListSupParameterDto;
import ru.team.up.dto.SupParameterDto;
import ru.team.up.sup.service.KafkaSupService;
import ru.team.up.sup.service.ParameterService;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class KafkaParameterListener {

    private final KafkaSupService kafkaSupService;
    private final ParameterService parameterService;

    @Autowired
    public KafkaParameterListener(KafkaSupService kafkaSupService, ParameterService parameterService) {
        this.kafkaSupService = kafkaSupService;
        this.parameterService = parameterService;
    }

    @PostConstruct
    public void init() {
        kafkaSupService.getAllModuleParameters();
    }

    @KafkaListener(topics = "${sup.kafka.topic.name}", containerFactory = "listDtoKafkaContainerFactory")
    public void listener(ListSupParameterDto listParameterDto) {
        for (SupParameterDto<?> parameterDto : listParameterDto.getList()) {
            log.debug("Получен параметр {}", parameterDto.getParameterName());
            parameterService.addParam(parameterDto);
        }
    }
}
