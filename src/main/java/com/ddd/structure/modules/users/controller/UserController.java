package com.ddd.structure.modules.users.controller;

import com.ddd.structure.modules.users.domain.entities.Users;
import com.ddd.structure.modules.users.dtos.CreateDTO;
import com.ddd.structure.modules.users.dtos.UpdateDTO;
import com.ddd.structure.modules.users.dtos.ResponseDTO;
import com.ddd.structure.modules.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> createUser(@Validated @RequestBody CreateDTO createDTO) {
        try {
            Users user = new Users(
                    null,
                    createDTO.name(),
                    createDTO.email(),
                    createDTO.password(),
                    createDTO.cpf(),
                    null,
                    null
            );
            Users createdUser = userService.saveUser(user);
            ResponseDTO responseDTO = new ResponseDTO(
                    createdUser.getId(),
                    createdUser.getName(),
                    createdUser.getEmail(),
                    createdUser.getCpf(),
                    createdUser.getPassword(), // Considere a segurança ao expor senhas
                    createdUser.getCreated_at(),
                    createdUser.getUpdated_at()
            );
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable UUID id) {
        Optional<Users> userOpt = userService.findById(id);
        if (userOpt.isPresent()) {
            Users user = userOpt.get();
            ResponseDTO responseDTO = new ResponseDTO(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getCpf(),
                    user.getPassword(), // Considere a segurança ao expor senhas
                    user.getCreated_at(),
                    user.getUpdated_at()
            );
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found with ID: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<ResponseDTO>> getAllUsers() {
        List<Users> users = userService.findAll();
        List<ResponseDTO> responseDTOs = users.stream().map(user -> new ResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCpf(),
                user.getPassword(), // Considere a segurança ao expor senhas
                user.getCreated_at(),
                user.getUpdated_at()
        )).collect(Collectors.toList());
        return responseDTOs.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(responseDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable UUID id, @Validated @RequestBody UpdateDTO updateDTO) {
        Optional<Users> existingUserOpt = userService.findById(id);
        if (!existingUserOpt.isPresent()) {
            return new ResponseEntity<>("User not found with ID: " + id, HttpStatus.NOT_FOUND);
        }
        Users existingUser = existingUserOpt.get();

        // Apply updates only if the fields are not null
        if (updateDTO.name() != null) existingUser.setName(updateDTO.name());
        if (updateDTO.email() != null) existingUser.setEmail(updateDTO.email());
        if (updateDTO.password() != null) existingUser.setPassword(updateDTO.password());
        if (updateDTO.cpf() != null) existingUser.setCpf(updateDTO.cpf());

        Users updatedUser = userService.saveUser(existingUser);
        ResponseDTO responseDTO = new ResponseDTO(
                updatedUser.getId(),
                updatedUser.getName(),
                updatedUser.getEmail(),
                updatedUser.getCpf(),
                updatedUser.getPassword(), // Considere a segurança ao expor senhas
                updatedUser.getCreated_at(),
                updatedUser.getUpdated_at()
        );
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable UUID id) {
        Optional<Users> userOpt = userService.findById(id);
        if (!userOpt.isPresent()) {
            return new ResponseEntity<>("User not found with ID: " + id, HttpStatus.NOT_FOUND);
        }
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
