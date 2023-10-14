package ru.otus.filinovich.dao.role;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.filinovich.domain.ERole;
import ru.otus.filinovich.domain.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
