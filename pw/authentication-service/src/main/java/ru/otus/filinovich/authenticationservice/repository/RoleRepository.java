package ru.otus.filinovich.authenticationservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.filinovich.authenticationservice.models.ERole;
import ru.otus.filinovich.authenticationservice.models.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
