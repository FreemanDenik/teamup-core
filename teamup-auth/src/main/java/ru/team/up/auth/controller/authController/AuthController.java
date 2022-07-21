package ru.team.up.auth.controller.authController;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.security.core.context.SecurityContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.team.up.auth.config.SuccessHandler;
import ru.team.up.auth.config.jwt.JwtProvider;
import ru.team.up.core.entity.*;
import ru.team.up.core.mappers.UserMapper;
import ru.team.up.core.monitoring.service.MonitorProducerService;
import ru.team.up.core.service.UserService;
import ru.team.up.dto.ControlDto;
import ru.team.up.dto.ParametersDto;
import ru.team.up.dto.UserDto;
import ru.team.up.sup.service.ParameterService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestController
@AllArgsConstructor
public class AuthController {
    private UserDetailsService userDetailsService;
    private JwtProvider jwtProvider;
    private PasswordEncoder passwordEncoder;
    private UserService userService;
    private MonitorProducerService monitorProducerService;

    @PostMapping("/registration")
    public AuthResponse registration(UserDto userDto , String password, String birthday) {
        if (!ParameterService.registrationEnabled.getValue()) {
            log.debug("Метод registration выключен параметром registrationEnabled = false");
            throw new RuntimeException("Method registration is disabled by parameter registrationEnabled");
        }
//        UserDto userDto = registrationRequest.getUserDto();
        User user = UserMapper.INSTANCE.mapUserFromDto(userDto);
        user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt(10)));
        user.setRole(Role.ROLE_USER);
        user.setAccountCreatedTime(LocalDate.now());
        user.setLastAccountActivity(LocalDateTime.now());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        user.setBirthday(LocalDate.parse(birthday, formatter));

        UserDetails newUser = userService.saveUser(user);

        String token = jwtProvider.generateToken(user.getEmail());

        Map<String, ParametersDto> monitoringParameters = new HashMap<>();
        monitoringParameters.put("Email", ParametersDto.builder()
                        .description("Почта")
                        .value(user.getEmail())
                .build());

        monitorProducerService.send(
                monitorProducerService.constructReportDto(SecurityContextHolder.getContext()
                                .getAuthentication().getPrincipal(),
                        ControlDto.MANUAL,
                        this.getClass(),
                        monitoringParameters)
        );
        return AuthResponse.builder().token(token).userDto(UserMapper.INSTANCE.mapUserToDto((User) newUser)).build();

    }

    @PostMapping("/login")
    public AuthResponse login( @RequestParam Map<String, String> request ) {
        if (!ParameterService.loginEnabled.getValue()) {
            log.debug("Метод login выключен параметром loginEnabled = false");
            throw new RuntimeException("Method login is disabled by parameter loginEnabled");
        }
        String token = null;
        UserDto userDto = null;
        Account account = (Account) userDetailsService.loadUserByUsername(request.get("auth_email"));
        if (Objects.nonNull(account)) {
            if (passwordEncoder.matches(request.get("auth_password"), account.getPassword())) {
                token = jwtProvider.generateToken(account.getEmail());
                if (account instanceof User) {
                    userDto = UserMapper.INSTANCE.mapUserToDto((User) account);
                } else if (account instanceof Moderator) {
                    userDto = UserMapper.INSTANCE.mapModeratorToDto((Moderator) account);
                } else if (account instanceof Admin) {
                    userDto = UserMapper.INSTANCE.mapAdminToDto((Admin) account);
                }

                Map<String, ParametersDto> monitoringParameters = new HashMap<>();
                monitoringParameters.put("Email", ParametersDto.builder()
                        .description("Почта")
                        .value(userDto.getEmail())
                        .build());

                monitorProducerService.send(
                        monitorProducerService.constructReportDto(SecurityContextHolder.getContext()
                                        .getAuthentication().getPrincipal(),
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
        if (!ParameterService.loginByGoogleEnabled.getValue()) {
            log.debug("Метод loginByGoogle выключен параметром loginByGoogleEnabled = false");
            throw new RuntimeException("Method loginByGoogle is disabled by parameter loginByGoogleEnabled");
        }
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
                Map<String, ParametersDto> monitoringParameters = new HashMap<>();
                monitoringParameters.put("Email", ParametersDto.builder()
                        .description("Почта")
                        .value(account.getEmail())
                        .build());

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
