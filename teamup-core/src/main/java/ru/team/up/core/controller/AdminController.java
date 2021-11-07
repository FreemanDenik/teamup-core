package ru.team.up.core.controller;

import lombok.AllArgsConstructor;
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
 */

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/private/account/admins")
public class AdminController {
    private AdminService adminService;

    /**
     * @return Результат работы метода adminService.getAllAdmins() в виде коллекции админов
     * в теле ResponseEntity
     */
    @GetMapping
    public ResponseEntity<List<Admin>> getAllAdmins() {
        return ResponseEntity.ok(adminService.getAllAdmins());
    }

    /**
     * @param id Значение ID админа
     * @return Результат работы метода adminService.getOneAdmin(id) в виде объекта Admin
     * в теле ResponseEntity
     */
    @GetMapping("/{id}")
    public ResponseEntity<Admin> getOneAdmin(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getOneAdmin(id));
    }

    /**
     * @param admin Создаваемый объект класса Admin
     * @return Результат работы метода adminService.saveAdmin(admin) в виде объекта Admin
     * в теле ResponseEntity
     */
    @PostMapping
    public ResponseEntity<Admin> createAdmin(@RequestBody @NotNull Admin admin) {
        return new ResponseEntity<>(adminService.saveAdmin(admin), HttpStatus.CREATED);
    }

    /**
     * @param admin Обновляемый объект класса Admin
     * @return Результат работы метода adminService.saveAdmin(admin) в виде объекта Admin
     * в теле ResponseEntity
     */
    @PatchMapping
    public ResponseEntity<Admin> updateAdmin(@RequestBody @NotNull Admin admin) {
        return ResponseEntity.ok(adminService.saveAdmin(admin));
    }

    /**
     * @param admin Удаляемый объект класса Admin
     * @return Объект ResponseEntity со статусом OK
     */
    @DeleteMapping
    public ResponseEntity<Admin> deleteAdmin(@RequestBody @NotNull Admin admin) {
        adminService.deleteAdmin(admin);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
