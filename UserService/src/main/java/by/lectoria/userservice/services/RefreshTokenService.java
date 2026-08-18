package by.lectoria.userservice.services;

import by.lectoria.userservice.dao.RefreshToken;
import by.lectoria.userservice.dao.User;
import by.lectoria.userservice.exceptions.NotFoundException;
import by.lectoria.userservice.model.Role;
import by.lectoria.userservice.repository.IRefreshTokenRepository;
import by.lectoria.userservice.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class RefreshTokenService {
    private final IRefreshTokenRepository iRefreshTokenRepository;
    private final AuthService authService;
    private final IUserRepository iUserRepository;

    public List<RefreshToken> findUserRefreshTokens() {
        User userFromDB = iUserRepository.findByLogin(authService.getAuthentication().getPrincipal().toString());

        return iRefreshTokenRepository.findRefreshTokenByUserId(userFromDB);
    }

    public List<RefreshToken> findUserRefTokensByAdmin(long id) {
        User userFromDB = iUserRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));

        return iRefreshTokenRepository.findRefreshTokenByUserId(userFromDB);
    }

    public ResponseEntity<?> deleteById(Long id) {
        Map<Object, Object> response = new HashMap<>();
        RefreshToken refreshTokenFromDB = iRefreshTokenRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Refresh token not found"));
        User userFromDB = iUserRepository.findByLogin(authService.getAuthentication().getPrincipal().toString());

        if (userFromDB.equals(refreshTokenFromDB.getUserId()) || userFromDB.getRole().equals(Role.ADMIN)) {
            iRefreshTokenRepository.deleteById(id);
            response.put("delete_refresh_token", "true");
            log.info("Delete refresh token with id = {} -> {}", id, authService.getAuthentication().getPrincipal());

            return ResponseEntity.ok(response);
        }

        response.put("delete_refresh_token", "false");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
