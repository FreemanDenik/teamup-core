package ru.team.up.sup.service.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.team.up.dto.AppModuleNameDto;
import ru.team.up.dto.SupParameterDto;
import ru.team.up.sup.dto.ListSupParameterDto;
import ru.team.up.sup.repository.SupRepository;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class KafkaMessageListener {

    private SupRepository supRepository;

    @Value(value = "${sup.kafka.system.name}")
    private AppModuleNameDto systemName;

    private KafkaTemplate<String, AppModuleNameDto> kafkaTemplate;

    @Autowired
    public KafkaMessageListener(KafkaTemplate<String, AppModuleNameDto> kafkaModuleNameTemplate,
                                SupRepository supRepository) {
        this.supRepository = supRepository;
        this.kafkaTemplate = kafkaModuleNameTemplate;
    }

    @PostConstruct
    public void init() {
        kafkaTemplate.send("initialization", systemName);
    }

    @KafkaListener(topics = "${sup.kafka.topic.name}", containerFactory = "listDtoKafkaContainerFactory")
    public void listener(ListSupParameterDto listParameterDto) {
        log.debug("Вход в метод listener");
        for (SupParameterDto<?> parameterDto : listParameterDto.getList()) {
            log.debug("Получен параметр {}", parameterDto.getParameterName());
            supRepository.add(parameterDto);
        }
    }
}
