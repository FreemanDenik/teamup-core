package ru.team.up.auth.controller.authController;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.team.up.auth.config.SuccessHandler;
import ru.team.up.auth.config.jwt.JwtProvider;
import ru.team.up.core.entity.*;
import ru.team.up.core.mappers.UserMapper;
import ru.team.up.core.monitoring.service.MonitorProducerService;
import ru.team.up.core.service.UserService;
import ru.team.up.dto.ControlDto;
import ru.team.up.dto.UserDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@AllArgsConstructor
public class AuthController {
    private UserDetailsService userDetailsService;
    private JwtProvider jwtProvider;
    private PasswordEncoder passwordEncoder;
    private UserService userService;
    private MonitorProducerService monitorProducerService;

    @PostMapping("/registration")
    public AuthResponse registration(@RequestBody RegistrationRequest registrationRequest) {
        UserDto userDto = registrationRequest.getUserDto();
        User user = UserMapper.INSTANCE.mapUserFromDto(userDto);
        user.setPassword(BCrypt.hashpw(registrationRequest.getPassword(), BCrypt.gensalt(10)));
        user.setRole(Role.ROLE_USER);
        user.setAccountCreatedTime(LocalDate.now());
        user.setLastAccountActivity(LocalDateTime.now());

        Account newUser = userService.saveUser(user);

        String token = jwtProvider.generateToken(user.getEmail());

        Map<String, Object> monitoringParameters = new HashMap<>();
        monitoringParameters.put("Email", user.getEmail());

        monitorProducerService.send(
                monitorProducerService.constructReportDto(SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                        ControlDto.MANUAL,
                        this.getClass(),
                        monitoringParameters)
        );
        return AuthResponse.builder().token(token).userDto(UserMapper.INSTANCE.mapUserToDto((User) newUser)).build();
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        String token = null;
        UserDto userDto = null;
        Account account = (Account) userDetailsService.loadUserByUsername(request.getUsername());
        if (Objects.nonNull(account)) {
            if (passwordEncoder.matches(request.getPassword(), account.getPassword())) {
                token = jwtProvider.generateToken(account.getEmail());
                if (account instanceof User) {
                    userDto = UserMapper.INSTANCE.mapUserToDto((User) account);
                } else if (account instanceof Moderator) {
                    userDto = UserMapper.INSTANCE.mapModeratorToDto((Moderator) account);
                } else if (account instanceof Admin) {
                    userDto = UserMapper.INSTANCE.mapAdminToDto((Admin) account);
                }

                Map<String, Object> monitoringParameters = new HashMap<>();
                monitoringParameters.put("Email", account.getEmail());

                monitorProducerService.send(
                        monitorProducerService.constructReportDto(SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                                ControlDto.MANUAL,
                                this.getClass(),
                                monitoringParameters)
                );
            }
        }
        return new AuthResponse(token, userDto);
    }

    @GetMapping("/loginByGoogle")
    public AuthResponse loginByGoogle() {
        UserDto userDto = null;
        String token = SuccessHandler.getToken();
        if (Objects.nonNull(token)) {
            String login = jwtProvider.getLoginFromToken(token);
            Account account = (Account) userDetailsService.loadUserByUsername(login);
            if (Objects.nonNull(account)) {
                if (account instanceof User) {
                    userDto = UserMapper.INSTANCE.mapUserToDto((User) account);
                } else if (account instanceof Moderator) {
                    userDto = UserMapper.INSTANCE.mapModeratorToDto((Moderator) account);
                } else if (account instanceof Admin) {
                    userDto = UserMapper.INSTANCE.mapAdminToDto((Admin) account);
                }
                Map<String, Object> monitoringParameters = new HashMap<>();
                monitoringParameters.put("Email", account.getEmail());

                monitorProducerService.send(
                        monitorProducerService.constructReportDto(SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                                ControlDto.MANUAL,
                                this.getClass(),
                                monitoringParameters)
                );
            }
        }
        return new AuthResponse(token, userDto);
    }
}
