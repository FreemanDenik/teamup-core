package ru.team.up.core.controller;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.team.up.core.entity.Admin;
import ru.team.up.core.repositories.AdminRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Alexey Tkachenko
 */
@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/private/account/admins")
public class AdminController {
    private AdminRepository adminRepository;

    @GetMapping
    public ResponseEntity<List<Admin>> getAllAdmins() {
        try {
            List<Admin> admins = new ArrayList<>(adminRepository.findAll());

            if (admins.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(admins, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Admin> getOneAdmin(@PathVariable Long id) {
        Optional<Admin> admin = adminRepository.findById(id);
        return admin.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) {
        try {
            Admin newAdmin = adminRepository.save(admin);
            return new ResponseEntity<>(newAdmin, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<Admin> updateAdmin(@RequestBody @NotNull Admin admin) {
        Optional<Admin> adminOptional = adminRepository.findById(admin.getId());
        if (adminOptional.isPresent()) {
            Admin updateAdmin = adminRepository.save(admin);
            return new ResponseEntity<>(updateAdmin, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping
    public ResponseEntity<Admin> deleteAdmin(@RequestBody Admin admin) {
        try {
            adminRepository.delete(admin);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
