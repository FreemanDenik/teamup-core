package ru.team.up.core.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.team.up.core.entity.Application;
import ru.team.up.dto.ApplicationDto;

import java.util.List;
@Mapper(uses = EventTranslator.class)
public interface ApplicationMapper {

    ApplicationMapper INSTANCE = Mappers.getMapper(ApplicationMapper.class);

    /**
     * @return мэппинг Applications в DTO
     */
    List<ApplicationDto> ApplicationsToDtoList(List<Application> applications);

    /**
     * @return мэппинг Application в DTO
     */
    ApplicationDto ApplicationToDto(Application application);
}
