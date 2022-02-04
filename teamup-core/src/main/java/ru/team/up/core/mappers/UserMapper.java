//package ru.team.up.core.mappers;
//
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.factory.Mappers;
//import ru.team.up.core.entity.User;
//import ru.team.up.dto.UserDto;
//
//@Mapper
//public interface UserMapper {
//
//    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
//
//    /**
//     * @return Возвращает DTO для сущности User
//     */
//    @Mapping(target = "userInterests.event.eventType", expression = "")
//    UserDto toDto(User user);
//}