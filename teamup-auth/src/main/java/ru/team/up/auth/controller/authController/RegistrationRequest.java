package ru.team.up.auth.controller.authController;

import lombok.Builder;
import lombok.Data;
import ru.team.up.dto.UserDto;

@Data
@Builder
public class RegistrationRequest {
    private String password;
    private UserDto userDto;
}
