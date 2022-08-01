package ru.team.up.core.mappers;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.team.up.core.entity.Account;
import ru.team.up.core.entity.Admin;
import ru.team.up.dto.AdminDto;
import java.util.List;

@Mapper
public interface AdminMapper {

    AdminMapper INSTANCE = Mappers.getMapper(AdminMapper.class);

    /**
     * @return мэппинг Admin в DTO
     */
    AdminDto mapAdminToDto (Admin admin);

    AdminDto mapAccountToDtoAdmin (Account account);

    List<AdminDto> mapAccountListToAdminDtoList(List<Account> adminList);

    /**
     * @return мэппинг List <Admin> в DTO
     */
    List<AdminDto> mapUserListToAdminDtoList(List<Admin> adminList);
}
