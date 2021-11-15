package ru.team.up.input.controlle.privateController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.team.up.core.entity.Admin;
import ru.team.up.core.service.AdminService;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Alexey Tkachenko
 *
 * @link localhost:8080/swagger-ui.html
 * Документация API
 */

@Slf4j
@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/private/account/admin")
public class AdminController {
    private AdminService adminService;

    /**
     * @return Результат работы метода adminService.getAllAdmins() в виде коллекции админов
     * в теле ResponseEntity
     */
    @GetMapping
    public ResponseEntity<List<Admin>> getAllAdmins() {
        log.debug("Старт метода ResponseEntity<List<Admin>> getAllAdmins()");

        ResponseEntity<List<Admin>> responseEntity = ResponseEntity.ok(adminService.getAllAdmins());
        log.debug("Получили ответ {}", responseEntity);

        return responseEntity;
    }

    /**
     * @param id Значение ID админа
     * @return Результат работы метода adminService.getOneAdmin(id) в виде объекта Admin
     * в теле ResponseEntity
     */
    @GetMapping("/{id}")
    public ResponseEntity<Admin> getOneAdmin(@PathVariable Long id) {
        log.debug("Старт метода ResponseEntity<Admin> getOneAdmin(@PathVariable Long id) с параметром {}", id);

        ResponseEntity<Admin> responseEntity = ResponseEntity.ok(adminService.getOneAdmin(id));
        log.debug("Получили ответ {}", responseEntity);

        return responseEntity;
    }

    /**
     * @param admin Создаваемый объект класса Admin
     * @return Результат работы метода adminService.saveAdmin(admin) в виде объекта Admin
     * в теле ResponseEntity
     */
    @PostMapping
    public ResponseEntity<Admin> createAdmin(@RequestParam String admin, @RequestBody @NotNull Admin adminCreate) {
        log.debug("Старт метода ResponseEntity<Admin> createAdmin(@RequestBody @NotNull Admin admin) с параметром {}", adminCreate);

        ResponseEntity<Admin> responseEntity = new ResponseEntity<>(adminService.saveAdmin(adminCreate), HttpStatus.CREATED);
        log.debug("Получили ответ {}", responseEntity);

        return responseEntity;
    }

    /**
     * @param admin Обновляемый объект класса Admin
     * @return Результат работы метода adminService.saveAdmin(admin) в виде объекта Admin
     * в теле ResponseEntity
     */
    @PatchMapping
    public ResponseEntity<Admin> updateAdmin(@RequestBody @NotNull Admin admin) {
        log.debug("Старт метода ResponseEntity<Admin> updateAdmin(@RequestBody @NotNull Admin admin) с параметром {}", admin);

        ResponseEntity<Admin> responseEntity = ResponseEntity.ok(adminService.saveAdmin(admin));
        log.debug("Получили ответ {}", responseEntity);

        return responseEntity;
    }

    /**
     * @param id Удаляемый объект класса Admin
     * @return Объект ResponseEntity со статусом OK
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Admin> deleteAdmin(@PathVariable Long id) {
        log.debug("Старт метода ResponseEntity<Admin> updateAdmin(@RequestBody @NotNull Admin admin) с параметром {}", id);

        adminService.deleteAdmin(id);

        ResponseEntity<Admin> responseEntity = new ResponseEntity<>(HttpStatus.ACCEPTED);
        log.debug("Получили ответ {}", responseEntity);

        return responseEntity;
    }
}
