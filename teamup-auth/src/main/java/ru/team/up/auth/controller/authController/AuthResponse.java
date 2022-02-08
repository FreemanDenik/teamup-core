package ru.team.up.auth.controller.authController;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.team.up.dto.UserDto;

@Data
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private UserDto userDto;
}
