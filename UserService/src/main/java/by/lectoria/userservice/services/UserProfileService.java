package by.lectoria.userservice.services;

import by.lectoria.userservice.config.PasswordEncoderConfig;
import by.lectoria.userservice.dao.User;
import by.lectoria.userservice.repository.IRefreshTokenRepository;
import by.lectoria.userservice.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserProfileService {
    private final IUserRepository iUserRepository;
    private final PasswordEncoderConfig passwordEncoderConfig;
    private final AuthService authService;
    private final IRefreshTokenRepository iRefreshTokenRepository;

    public ResponseEntity<?> updateProfile(User user) {
        Map<Object, Object> response = new HashMap<>();
        User userFromDB = iUserRepository.findByLogin(authService.getAuthentication().getPrincipal().toString());

        if (userFromDB == null) {
            response.put("error", "update user");
            response.put("text", "The user could not be update. User "
                    + authService.getAuthentication().getPrincipal().toString() + " not found");
            log.warn("UPDATE_PROFILE -> The user could not be updated. User {} not found",
                    authService.getAuthentication().getPrincipal().toString());

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (user.getEmail() != null) {
            userFromDB.setEmail(user.getEmail());
            log.info("The user's email address with id {} has been updated -> {}",
                    userFromDB.getId(), authService.getAuthentication().getPrincipal());
        }

        if (user.getPassword() != null) {
            userFromDB.setPassword(passwordEncoderConfig.getPasswordEncoder().encode(user.getPassword()));
            iRefreshTokenRepository.deleteByUserId(userFromDB);
            log.info("The password of the user with the id {} has been updated -> {}",
                    userFromDB.getId(), authService.getAuthentication().getPrincipal());
        }

        if (user.getFirstName() != null) {
            userFromDB.setFirstName(user.getFirstName());
            log.info("The FirstName of the user with the id {} has been updated -> {}",
                    userFromDB.getId(), authService.getAuthentication().getPrincipal());
        }

        if (user.getLastName() != null) {
            userFromDB.setLastName(user.getLastName());
            log.info("The LastName of the user with the id {} has been updated -> {}",
                    userFromDB.getId(), authService.getAuthentication().getPrincipal());
        }

        userFromDB.setDateModified(LocalDateTime.now());
        iUserRepository.save(userFromDB);
        response.put("update_user", "true");

        return ResponseEntity.ok(response);
    }
}
