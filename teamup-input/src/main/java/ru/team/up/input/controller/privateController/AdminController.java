package ru.team.up.input.controller.privateController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.team.up.core.entity.Account;
import ru.team.up.core.entity.Admin;
import ru.team.up.core.exception.UserNotFoundIDException;
import ru.team.up.core.mappers.AdminMapper;
import ru.team.up.core.mappers.UserMapper;
import ru.team.up.core.monitoring.service.MonitorProducerService;
import ru.team.up.core.service.AdminService;
import ru.team.up.dto.AdminDto;
import ru.team.up.dto.ControlDto;
import ru.team.up.dto.ParametersDto;
import ru.team.up.dto.UserDto;
import ru.team.up.input.response.AdminDtoListResponse;
import ru.team.up.input.response.AdminDtoResponse;
import ru.team.up.sup.service.ParameterService;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alexey Tkachenko
 * @link localhost:8080/swagger-ui.html
 * Документация API
 */

@Slf4j
@Tag(name = "Admin Private Controller", description = "Admin API")
@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/private/account/admin")
public class AdminController {
    private AdminService adminService;
    private MonitorProducerService monitorProducerService;

    /**
     * @return Результат работы метода adminService.getAllAdmins() в виде коллекции админов
     * в обьекте AdminDtoListResponse
     */
    @Operation(summary = "Получение списка всех администраторов") // ТЮТЬ
    @GetMapping
    public AdminDtoListResponse getAllAdmins() {
        log.debug("Старт метода AdminDtoListResponse getAllAdmins()");
        if (!ParameterService.getAllAdminsEnabled.getValue()) {
            log.debug("Метод getAllAdmins выключен параметром getAllAdminsEnabled = false");
            throw new RuntimeException("Method getAllAdmins is disabled by parameter getAllAdminsEnabled");
        }
        List<AdminDto> allAdmins = AdminMapper.INSTANCE.mapAccountListToAdminDtoList(adminService.getAllAdmins());
        Map<String, ParametersDto> monitoringParameters = new HashMap<>();

        ParametersDto amountAdmins = ParametersDto.builder()
                .description("Количество админов")
                .value(allAdmins.size())
                .build();
        monitoringParameters.put("Количество админов", amountAdmins);


        monitorProducerService.send(
                monitorProducerService.constructReportDto(SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                        ControlDto.MANUAL,
                        this.getClass(),
                        monitoringParameters)
        );
        return AdminDtoListResponse.builder().adminDtoList(allAdmins).build();
    }

    /**
     * @param id Значение ID админа
     * @return Результат работы метода adminService.getOneAdmin(id) в виде объекта AdminDtoResponse
     */
    @Operation(summary = "Получение администратора по id")
    @GetMapping("/{id}")
    public AdminDtoResponse getOneAdmin(@PathVariable Long id) {
        log.debug("Старт метода AdminDtoResponse getOneAdmin(@PathVariable Long id) с параметром {}", id);

        AdminDto admin = AdminMapper.INSTANCE.mapAccountToDtoAdmin(adminService.getOneAdmin(id));
        if (!ParameterService.getOneAdminEnabled.getValue()) {
            log.debug("Метод getOneAdmin выключен параметром getOneAdminEnabled = false");
            throw new RuntimeException("Method getOneAdmin is disabled by parameter getOneAdminEnabled");
        }
        Map<String, ParametersDto> monitoringParameters = new HashMap<>();
        ParametersDto accaountId = ParametersDto.builder()
                .description("ID")
                .value(admin.getId())
                .build();

        ParametersDto accountEmail = ParametersDto.builder()
                .description("Email")
                .value(admin.getEmail())
                .build();

        ParametersDto accountUsername = ParametersDto.builder()
                .description("Имя")
                .value(admin.getUsername())
                .build();

        monitoringParameters.put("ID", accaountId);
        monitoringParameters.put("Email", accountEmail);
        monitoringParameters.put("Имя", accountUsername);

        monitorProducerService.send(
                monitorProducerService.constructReportDto(SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                        ControlDto.MANUAL,
                        this.getClass(),
                        monitoringParameters)
        );
        return AdminDtoResponse.builder().adminDto(admin).build();
    }

    /**
     * @param adminCreate Создаваемый объект класса Admin
     * @return Результат работы метода adminService.saveAdmin(admin) в виде объекта AdminDtoResponse
     */
    @Operation(summary = "Создание нового администратора")
    @PostMapping
    public AdminDtoResponse createAdmin(@RequestBody @NotNull Admin adminCreate) {
        log.debug("Старт метода AdminDtoResponse createAdmin(@RequestBody @NotNull Admin admin) с параметром {}", adminCreate);
        if (!ParameterService.createAdminEnabled.getValue()) {
            log.debug("Метод createAdmin выключен параметром createAdminEnabled = false");
            throw new RuntimeException("Method createAdmin is disabled by parameter createAdminEnabled");
        }
        AdminDto adminDto = AdminMapper.INSTANCE.mapAdminToDto((Admin) adminService.saveAdmin(adminCreate));
        log.debug("Получили ответ {}", adminDto);

        Map<String, ParametersDto> monitoringParameters = new HashMap<>();
        ParametersDto accaountId = ParametersDto.builder()
                .description("ID")
                .value(adminCreate.getId())
                .build();

        ParametersDto accountEmail = ParametersDto.builder()
                .description("Email")
                .value(adminCreate.getEmail())
                .build();

        ParametersDto accountUsername = ParametersDto.builder()
                .description("Имя")
                .value(adminCreate.getUsername())
                .build();

        monitoringParameters.put("ID", accaountId);
        monitoringParameters.put("Email", accountEmail);
        monitoringParameters.put("Имя", accountUsername);

        monitorProducerService.send(
                monitorProducerService.constructReportDto(SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                        ControlDto.MANUAL,
                        this.getClass(),
                        monitoringParameters)
        );
        return AdminDtoResponse.builder().adminDto(adminDto).build();
    }

    /**
     * @param admin Обновляемый объект класса Admin
     * @return Результат работы метода adminService.saveAdmin(admin) в виде объекта AdminDtoResponse
     */
    @Operation(summary = "Обновление данных администратора")
    @PutMapping("/{id}")
    public AdminDtoResponse updateAdmin(@PathVariable Long id, @RequestBody @NotNull Admin admin) {
        log.debug("Старт метода AdminDtoResponse updateAdmin(@RequestBody @NotNull Admin admin) с параметром {}", admin);

        if (!ParameterService.updateAdminEnabled.getValue()) {
            log.debug("Метод updateAdmin выключен параметром updateAdminEnabled = false");
            throw new RuntimeException("Method updateAdmin is disabled by parameter updateAdminEnabled");
        }

        log.debug("Проверка наличия обновляемого администратора в БД");
        if (!(adminService.existsById(id)) && !(id.equals(admin.getId()))) {
            log.error("Пользователь не найден");
            throw new UserNotFoundIDException(id);
        }

        AdminDto adminDto = AdminMapper.INSTANCE.mapAdminToDto(adminService.updateAdmin(admin));
        log.debug("Получили ответ {}", adminDto);

        Map<String, ParametersDto> monitoringParameters = new HashMap<>();
        ParametersDto accaountId = ParametersDto.builder()
                .description("ID")
                .value(admin.getId())
                .build();

        ParametersDto accountEmail = ParametersDto.builder()
                .description("Email")
                .value(admin.getEmail())
                .build();

        ParametersDto accountUsername = ParametersDto.builder()
                .description("Имя")
                .value(admin.getUsername())
                .build();

        monitoringParameters.put("ID", accaountId);
        monitoringParameters.put("Email", accountEmail);
        monitoringParameters.put("Имя", accountUsername);

        monitorProducerService.send(
                monitorProducerService.constructReportDto(SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                        ControlDto.MANUAL,
                        this.getClass(),
                        monitoringParameters)
        );
        return AdminDtoResponse.builder().adminDto(adminDto).build();
    }


    /**
     * @param id Удаляемый объект класса Admin
     * @return Объект ResponseEntity со статусом OK
     */
    @Operation(summary = "Удаление администратора по id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Admin> deleteAdmin(@PathVariable Long id) {
        log.debug("Старт метода ResponseEntity<Admin> updateAdmin(@RequestBody @NotNull Admin admin) с параметром {}", id);

        if (!ParameterService.deleteAdminFromAdminControllerEnabled.getValue()) {
            log.debug("Метод deleteAdmin выключен параметром deleteAdminFromAdminControllerEnabled = false");
            throw new RuntimeException("Method deleteAdmin is disabled by parameter deleteAdminFromAdminControllerEnabled");
        }

        adminService.deleteAdmin(id);

        ResponseEntity<Admin> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        log.debug("Получили ответ {}", responseEntity);

        Map<String, ParametersDto> monitoringParameters = new HashMap<>();
        ParametersDto administratorId = ParametersDto.builder()
                .description("ID")
                .value(id)
                .build();

        monitoringParameters.put("ID", administratorId);

        monitorProducerService.send(
                monitorProducerService.constructReportDto(SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                        ControlDto.MANUAL,
                        this.getClass(),
                        monitoringParameters)
        );
        return responseEntity;
    }
}
