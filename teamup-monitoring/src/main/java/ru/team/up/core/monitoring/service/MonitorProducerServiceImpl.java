package ru.team.up.core.monitoring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.team.up.dto.*;
import ru.team.up.core.entity.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    public Map<String, Object> parameters(String key, Object value) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(key, value);
        return parameters;
    }

    @Override
    public ReportDto constructReportDto(Object principal, ControlDto control,
                                        String reportName, AppModuleNameDto appModuleName,
                                        ReportStatusDto reportStatusDto,
                                        String param1, Object param2) {
        ReportDto reportDto = ReportDto.builder()
                .control(control)
                .reportName(reportName)
                .appModuleName(appModuleName)
                .reportStatus(reportStatusDto)
                .time(new Date())
                .parameters(parameters(param1, param2)).build();

        if (principal.toString().equals("anonymousUser")) {
            reportDto.setInitiatorId(0L);
            reportDto.setInitiatorName("anonymousUser");
            reportDto.setInitiatorType(InitiatorTypeDto.USER);
        } else if (principal instanceof Admin) {
            Admin admin = (Admin) principal;
            reportDto.setInitiatorId(admin.getId());
            reportDto.setInitiatorName(admin.getUsername());
            reportDto.setInitiatorType(InitiatorTypeDto.ADMIN);
        } else if (principal instanceof User) {
            User user = (User) principal;
            reportDto.setInitiatorId(user.getId());
            reportDto.setInitiatorName(user.getUsername());
            reportDto.setInitiatorType(InitiatorTypeDto.USER);
        }

        return reportDto;
    }

    @Override
    public void send(ReportDto content) {
        log.debug(topic + " ------------ " + content);
        kafkaTemplate.send(topic, content);
    }
}
