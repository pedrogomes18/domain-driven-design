package com.ddd.structure.modules.users.dtos;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateDTO(
        @NotBlank(message = "Name cannot be blank")
        @Size(max = 255, message = "Name should not exceed 255 characters")
        String name,

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Email should be valid")
        @Size(max = 255, message = "Email should not exceed 255 characters")
        String email,

        @NotBlank(message = "Password cannot be blank")
        @Size(min = 6, message = "Password should be at least 6 characters long")
        String password,

        @NotBlank(message = "CPF cannot be blank")
        @Size(max = 255, message = "CPF should not exceed 255 characters")
        String cpf
) {
}
