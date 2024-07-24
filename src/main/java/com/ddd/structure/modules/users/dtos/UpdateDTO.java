package com.ddd.structure.modules.users.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UpdateDTO(
        @Size(max = 255, message = "Name should not exceed 255 characters")
        String name,

        @Email(message = "Email should be valid")
        @Size(max = 255, message = "Email should not exceed 255 characters")
        String email,

        @Size(min = 6, message = "Password should be at least 6 characters long")
        String password,

        @Size(max = 255, message = "CPF should not exceed 255 characters")
        String cpf
) {
}
