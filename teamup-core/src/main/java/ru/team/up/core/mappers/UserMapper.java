package ru.team.up.core.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.team.up.core.entity.Admin;
import ru.team.up.core.entity.Moderator;
import ru.team.up.core.entity.User;
import ru.team.up.dto.UserDto;

import java.util.List;

@Mapper(uses = AgeTranslator.class)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * @return мэппинг User в DTO
     */
    @Mapping(target = "age", source = "birthday")
    UserDto mapUserToDto(User user);

    /**
     * @return мэппинг Admin в DTO
     */
    UserDto mapAdminToDto(Admin admin);

    /**
     * @return мэппинг Moderator в DTO
     */
    UserDto mapModeratorToDto(Moderator moderator);

    /**
     * @return мэппинг UserDto в User
     */
    @Mapping(target = "birthday", source = "age")
     User mapUserFromDto(UserDto userDto);

    /**
     * @return мэппинг List<User> в List<UserDto>
     */
    List<UserDto> mapUserListToUserDtoList(List<User> userList);

    /**
     * @return мэппинг UserDto в Admin
     */
    Admin mapAdminFromDto(UserDto userDto);

    /**
     * @return мэппинг UserDto в Admin
     */
    Moderator mapModeratorFromDto(UserDto userDto);

}