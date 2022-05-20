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
import ru.team.up.core.mappers.UserMapper;
import ru.team.up.core.monitoring.service.MonitorProducerService;
import ru.team.up.core.service.AdminService;
import ru.team.up.dto.ControlDto;
import ru.team.up.dto.UserDto;

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
     * в теле ResponseEntity
     */
    @Operation(summary = "Получение списка всех администраторов")
    @GetMapping
    public ResponseEntity<List<Account>> getAllAdmins() {
        log.debug("Старт метода ResponseEntity<List<Admin>> getAllAdmins()");

        List<Account> allAdmins = adminService.getAllAdmins();

        ResponseEntity<List<Account>> responseEntity = ResponseEntity.ok(allAdmins);
        log.debug("Получили ответ {}", responseEntity);

        Map<String, Object> monitoringParameters = new HashMap<>();
        monitoringParameters.put("Количество админов", allAdmins.size());

        monitorProducerService.send(
                monitorProducerService.constructReportDto(SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                        ControlDto.MANUAL,
                        this.getClass(),
                        monitoringParameters)
        );

        return responseEntity;
    }

    /**
     * @param id Значение ID админа
     * @return Результат работы метода adminService.getOneAdmin(id) в виде объекта Admin
     * в теле ResponseEntity
     */
    @Operation(summary = "Получение администратора по id")
    @GetMapping("/{id}")
    public ResponseEntity<Account> getOneAdmin(@PathVariable Long id) {
        log.debug("Старт метода ResponseEntity<Admin> getOneAdmin(@PathVariable Long id) с параметром {}", id);
        Account admin = adminService.getOneAdmin(id);
        ResponseEntity<Account> responseEntity = ResponseEntity.ok(admin);
        log.debug("Получили ответ {}", responseEntity);

        Map<String, Object> monitoringParameters = new HashMap<>();
        monitoringParameters.put("ID", admin.getId());
        monitoringParameters.put("Email", admin.getEmail());
        monitoringParameters.put("Имя", admin.getUsername());

        monitorProducerService.send(
                monitorProducerService.constructReportDto(SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                        ControlDto.MANUAL,
                        this.getClass(),
                        monitoringParameters)
        );
        return responseEntity;
    }

    /**
     * @param adminCreate Создаваемый объект класса Admin
     * @return Результат работы метода adminService.saveAdmin(admin) в виде объекта Admin
     * в теле ResponseEntity
     */
    @Operation(summary = "Создание нового администратора")
    @PostMapping
    public ResponseEntity<Account> createAdmin(@RequestBody @NotNull Admin adminCreate) {
        log.debug("Старт метода ResponseEntity<Admin> createAdmin(@RequestBody @NotNull Admin admin) с параметром {}", adminCreate);
        ResponseEntity<Account> responseEntity =
                new ResponseEntity<>(adminService.saveAdmin(adminCreate), HttpStatus.CREATED);
        log.debug("Получили ответ {}", responseEntity);

        Map<String, Object> monitoringParameters = new HashMap<>();
        monitoringParameters.put("ID", adminCreate.getId());
        monitoringParameters.put("Email", adminCreate.getEmail());
        monitoringParameters.put("Имя", adminCreate.getUsername());

        monitorProducerService.send(
                monitorProducerService.constructReportDto(SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                        ControlDto.MANUAL,
                        this.getClass(),
                        monitoringParameters)
        );
        return responseEntity;
    }

    /**
     * @param admin Обновляемый объект класса Admin
     * @return Результат работы метода adminService.saveAdmin(admin) в виде объекта Admin
     * в теле ResponseEntity
     */
    @Operation(summary = "Обновление данных администратора")
    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAdmin(@PathVariable Long id, @RequestBody @NotNull Admin admin) {
        log.debug("Старт метода ResponseEntity<Admin> updateAdmin(@RequestBody @NotNull Admin admin) с параметром {}", admin);
        log.debug("Проверка наличия обновляемого администратора в БД");
        if (adminService.existsById(id) && id.equals(admin.getId())) {
            ResponseEntity<Account> responseEntity = ResponseEntity.ok(adminService.updateAdmin(admin));
            log.debug("Получили ответ {}", responseEntity);

            Map<String, Object> monitoringParameters = new HashMap<>();
            monitoringParameters.put("ID", admin.getId());
            monitoringParameters.put("Email", admin.getEmail());
            monitoringParameters.put("Имя", admin.getUsername());

            monitorProducerService.send(
                    monitorProducerService.constructReportDto(SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                            ControlDto.MANUAL,
                            this.getClass(),
                            monitoringParameters)
            );
            return responseEntity;
        } else {
            log.debug("Неверно указан id");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param id Удаляемый объект класса Admin
     * @return Объект ResponseEntity со статусом OK
     */
    @Operation(summary = "Удаление администратора по id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Admin> deleteAdmin(@PathVariable Long id) {
        log.debug("Старт метода ResponseEntity<Admin> updateAdmin(@RequestBody @NotNull Admin admin) с параметром {}", id);
        adminService.deleteAdmin(id);

        ResponseEntity<Admin> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        log.debug("Получили ответ {}", responseEntity);

        Map<String, Object> monitoringParameters = new HashMap<>();
        monitoringParameters.put("ID", id);

        monitorProducerService.send(
                monitorProducerService.constructReportDto(SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                        ControlDto.MANUAL,
                        this.getClass(),
                        monitoringParameters)
        );
        return responseEntity;
    }
}
