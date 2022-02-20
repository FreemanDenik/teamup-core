package ru.team.up.core.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.team.up.core.entity.Event;
import ru.team.up.core.entity.User;
import ru.team.up.dto.EventDto;

import java.util.List;

@Mapper(uses = EventTranslator.class)
public interface EventMapper {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    /**
     * @return мэппинг Event в DTO
     */
    List<EventDto> mapEventsToDtoEventList(List<Event> eventList);

    /**
     * @return мэппинг Event в DTO
     */
    EventDto  mapEventToEventDto(Event event);

    /**
     * @return мэппинг Dto Event в Event
     */
    Event mapEventsToDtoEventList(EventDto eventDto);
}
