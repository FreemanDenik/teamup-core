package ru.team.up.core.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.team.up.core.entity.Account;
import ru.team.up.core.entity.Moderator;

import ru.team.up.dto.ModeratorDto;


import java.util.List;

@Mapper
public interface ModeratorMapper {

    ModeratorMapper INSTANCE = Mappers.getMapper(ModeratorMapper.class);

    /**
     * @return мэппинг Moderator в DTO
     */
    ModeratorDto mapModeratorToDto(Moderator moderator);

    ModeratorDto mapAccountToDto(Account moderator);

    List<ModeratorDto> mapAccountListToModeratorDtoList(List<Account> moderatorList);

    /**
     * @return мэппинг List <Moderator> в DTO
     */
    List<ModeratorDto> mapModeratorListToModeratorDtoList(List<Moderator> moderatorList);
}
