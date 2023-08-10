package ru.otus.filinovich.dao.user;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.filinovich.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUsername(String username);
}
