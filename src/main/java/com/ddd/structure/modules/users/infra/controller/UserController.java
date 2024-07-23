package com.ddd.structure.modules.users.infra.controller;

import com.ddd.structure.modules.users.infra.domain.entities.Users;
import com.ddd.structure.modules.users.services.CreateUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final CreateUserService createUserService;

    @Autowired
    public UserController(CreateUserService createUserService) {
        this.createUserService = createUserService;
    }

    @PostMapping
    public ResponseEntity<Users> createUser(@Validated @RequestBody Users user) {
        try {
            Users createdUser = createUserService.createUser(user);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable UUID id) {
        Optional<Users> user = createUserService.findById(id); // Note: Ensure that findById is implemented in CreateUserService
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Outros métodos de endpoint
}
