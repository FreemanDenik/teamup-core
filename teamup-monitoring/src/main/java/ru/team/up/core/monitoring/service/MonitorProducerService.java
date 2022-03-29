package ru.team.up.core.monitoring.service;

import ru.team.up.dto.ReportDto;

public interface MonitorProducerService {
    void send(ReportDto content);
}
