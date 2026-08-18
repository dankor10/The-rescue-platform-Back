package by.lectoria.userservice.services;

import by.lectoria.userservice.config.PasswordEncoderConfig;
import by.lectoria.userservice.dao.User;
import by.lectoria.userservice.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final IUserRepository iUserRepository;
    private final PasswordEncoderConfig passwordEncoderConfig;

    public ResponseEntity<?> registrationUser(User user) {
        Map<Object, Object> response = new HashMap<>();
        User userFromDb = new User();
        boolean isPassed = true;

        User userLoginFromDb = iUserRepository.findByLogin(user.getLogin());

        if (userLoginFromDb != null) {
            response.put("error_login", "This login is used");
            log.warn("This login is used: {}", user.getLogin());
            isPassed = false;
        }

        if (user.getLogin() == null) {
            response.put("error_login", "The login not be empty");
            log.warn("The login not be empty");
            isPassed = false;
        }

        if (user.getPassword() == null) {
            response.put("error_password", "The password not be empty");
            log.warn("The password not be empty");
            isPassed = false;
        }

        if (isPassed) {
            userFromDb.setLogin(user.getLogin());
            userFromDb.setPassword(passwordEncoderConfig.getPasswordEncoder().encode(user.getPassword()));
            userFromDb.setFirstName(user.getFirstName());
            userFromDb.setLastName(user.getLastName());
            userFromDb.setEmail(user.getEmail());
            userFromDb.setRole(user.getRole());
            userFromDb.setDateCreated(LocalDateTime.now());
            userFromDb.setBlocked(false);
            userFromDb.setProtectionFromDeletion(false);
            iUserRepository.save(userFromDb);
            response.put("registration_user", "true");
            log.info("The user with id {} is registered. Login: {}", userFromDb.getId(), user.getLogin());
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(response);
    }
}

