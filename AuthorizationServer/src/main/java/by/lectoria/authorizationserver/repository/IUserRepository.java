package by.lectoria.authorizationserver.repository;

import by.lectoria.authorizationserver.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);
}
