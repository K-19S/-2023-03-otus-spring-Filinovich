package ru.otus.filinovich.dao.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.filinovich.domain.User;

public interface UserRepository extends MongoRepository<User, String> {

    User findUserByUsername(String username);
}
