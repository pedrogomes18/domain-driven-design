package com.ddd.structure.modules.users.services;

import com.ddd.structure.modules.users.domain.entities.Users;
import com.ddd.structure.modules.users.domain.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UsersRepository usersRepository;

    public Optional<Users> findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    public Optional<Users> findByCpf(String cpf) {
        return usersRepository.findByCpf(cpf);
    }

    public Users saveUser(Users user) {
        return usersRepository.save(user);
    }

    public Optional<Users> findById(UUID id) {
        return usersRepository.findById(id);
    }

    public List<Users> findAll() {
        return usersRepository.findAll();
    }

    public void deleteById(UUID id) {
        usersRepository.deleteById(id);
    }
}
