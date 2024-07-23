package com.ddd.structure.modules.users.infra.domain.repositories;

import com.ddd.structure.modules.users.infra.domain.entities.Users;
import com.ddd.structure.modules.users.repositories.IUsersRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UsersRepository implements IUsersRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)  // Transação somente leitura para consultas
    public Optional<Users> findByEmail(String email) {
        try {
            String jpql = "SELECT u FROM Users u WHERE u.email = :email";
            TypedQuery<Users> query = entityManager.createQuery(jpql, Users.class);
            query.setParameter("email", email);
            return query.getResultList().stream().findFirst();
        } catch (Exception e) {
            // Adicionar um log ou tratar exceções conforme necessário
            return Optional.empty();
        }
    }

    @Override
    @Transactional(readOnly = true)  // Transação somente leitura para consultas
    public Optional<Users> findByCpf(String cpf) {
        try {
            String jpql = "SELECT u FROM Users u WHERE u.cpf = :cpf";
            TypedQuery<Users> query = entityManager.createQuery(jpql, Users.class);
            query.setParameter("cpf", cpf);
            return query.getResultList().stream().findFirst();
        } catch (Exception e) {
            // Adicionar um log ou tratar exceções conforme necessário
            return Optional.empty();
        }
    }

    @Override
    @Transactional  // Transação necessária para persistência e atualização
    public Users save(Users user) {
        try {
            if (user.getId() == null) {
                entityManager.persist(user);
                return user;
            } else {
                return entityManager.merge(user);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to save user", e);
        }
    }

    @Override
    @Transactional(readOnly = true)  // Transação somente leitura para consultas
    public Optional<Users> findById(UUID id) {
        try {
            Users user = entityManager.find(Users.class, id);
            return Optional.ofNullable(user);
        } catch (Exception e) {
            // Adicionar um log ou tratar exceções conforme necessário
            return Optional.empty();
        }
    }

    @Override
    @Transactional  // Transação necessária para remover
    public void deleteById(UUID id) {
        try {
            Users user = entityManager.find(Users.class, id);
            if (user != null) {
                entityManager.remove(user);
            }
        } catch (Exception e) {
            // Adicionar um log ou tratar exceções conforme necessário
            throw new RuntimeException("Failed to delete user", e);
        }
    }
}
