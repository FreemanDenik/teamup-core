package ru.team.up.auth.controller.authController;

import lombok.Data;

@Data
public class AuthRequest {

    private String username;
    private String password;
}
