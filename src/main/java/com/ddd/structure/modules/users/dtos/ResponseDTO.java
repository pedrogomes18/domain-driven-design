package com.ddd.structure.modules.users.dtos;

import java.util.Date;
import java.util.UUID;

public record ResponseDTO(
        UUID id,
        String name,
        String email,
        String cpf,
        String password,
        Date created_at,
        Date updated_at
) {
}
