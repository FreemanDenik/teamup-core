package ru.team.up.auth.controller.authController;

import lombok.Data;
import ru.team.up.dto.UserDto;

@Data
public class RegistrationRequest {
    private String password;
    private UserDto userDto;
}
