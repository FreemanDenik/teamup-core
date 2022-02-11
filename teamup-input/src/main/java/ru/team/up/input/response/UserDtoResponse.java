package ru.team.up.input.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.team.up.dto.UserDto;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoResponse {

    private UserDto userDto;
}