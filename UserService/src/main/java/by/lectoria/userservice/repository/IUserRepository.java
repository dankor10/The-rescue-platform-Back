package by.lectoria.userservice.repository;

import by.lectoria.userservice.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IUserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);
    List<User> findByLoginContainingIgnoreCase(String login);
    List<User> findByFirstNameContainingIgnoreCase(String firstName);
    List<User> findByLastNameContainingIgnoreCase(String lastName);
    List<User> findByEmailContainingIgnoreCase(String email);
}
