package com.ddd.structure.modules.users.services;

import com.ddd.structure.modules.users.infra.domain.entities.Users;
import com.ddd.structure.modules.users.repositories.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CreateUserService {

    private final IUsersRepository usersRepository;

    @Autowired
    public CreateUserService(IUsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Users createUser(Users user) {
        // Lógica para criar um usuário, pode incluir validação, etc.
        return usersRepository.save(user);
    }

    public Optional<Users> findById(UUID id) {
        return usersRepository.findById(id);
    }
}
