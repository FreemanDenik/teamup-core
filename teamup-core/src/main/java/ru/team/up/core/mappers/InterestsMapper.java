package ru.team.up.core.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.team.up.core.entity.Interests;
import ru.team.up.dto.InterestsDto;

@Mapper(uses = AgeTranslator.class)
public interface InterestsMapper {

    InterestsMapper INSTANCE = Mappers.getMapper(InterestsMapper.class);

    /**
     * @return мэппинг Interests в DTO
     */
    InterestsDto mapInterestsToDto(Interests interests);

}