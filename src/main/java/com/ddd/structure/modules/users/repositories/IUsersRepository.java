package com.ddd.structure.modules.users.repositories;

import com.ddd.structure.modules.users.infra.domain.entities.Users;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUsersRepository {
    Optional<Users> findByEmail(String email);
    Optional<Users> findByCpf(String cpf);
    Users save(Users user);
    Optional<Users> findById(UUID id);
    void deleteById(UUID id);
}
