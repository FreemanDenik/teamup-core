package ru.team.up.auth.controller.authController;

import lombok.Data;
import ru.team.up.dto.UserDto;

import javax.validation.constraints.NotEmpty;

@Data
public class RegistrationRequest {

    @NotEmpty
    private String password;

    @NotEmpty
    private UserDto userDto;
}
