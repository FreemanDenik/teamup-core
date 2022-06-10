package ru.team.up.core.monitoring.service;

import ru.team.up.dto.*;

import java.util.Map;

public interface MonitorProducerService {
    ReportDto constructReportDto(Object principal, ControlDto control, Class cl, Map<String, ParametersDto> params);

    void send(ReportDto content);
}
