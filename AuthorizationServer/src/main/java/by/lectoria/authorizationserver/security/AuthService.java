package by.lectoria.authorizationserver.security;

import by.lectoria.authorizationserver.dao.RefreshToken;
import by.lectoria.authorizationserver.dao.User;
import by.lectoria.authorizationserver.exceptions.JwtAuthenticationException;
import by.lectoria.authorizationserver.repository.IRefreshTokenRepository;
import by.lectoria.authorizationserver.repository.IUserRepository;
import by.lectoria.authorizationserver.services.RequestService;
import by.lectoria.authorizationserver.services.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final IRefreshTokenRepository iRefreshTokenRepository;
    private final IUserRepository iUserRepository;
    private final RequestService requestService;

    public JwtResponse login(String userAgent, HttpServletRequest request, @NonNull JwtRequest authRequest) {
        final User user = userService.getByLogin(authRequest.getLogin())
                .orElseThrow(() -> new JwtAuthenticationException("User not found", HttpStatus.FORBIDDEN));


        if (user.getBlocked()) {
            log.warn("The user = {} with id = {} is blocked. Authorization failed", user.getLogin(), user.getId());
            throw new JwtAuthenticationException("The user " + user.getLogin() + "is blocked. Authorization failed",
                    HttpStatus.FORBIDDEN);
        }

        if (passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);
            String clientIp = requestService.getClientIp(request);
            RefreshToken refToken = new RefreshToken();
            refToken.setUserId(user);
            refToken.setName(refreshToken);
            refToken.setUserAgent(userAgent);
            refToken.setIpAddress(clientIp);
            user.setLastTimeOnline(LocalDateTime.now());
            iUserRepository.save(user);
            iRefreshTokenRepository.save(refToken);

            return new JwtResponse(accessToken, refreshToken);
        } else {
            log.warn("Invalid password. User = {} with id = {}", user.getLogin(), user.getId());
            throw new JwtAuthenticationException("Invalid password", HttpStatus.FORBIDDEN);
        }
    }

    public JwtResponse getAccessToken(@NonNull String refreshToken, HttpServletRequest request) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = iRefreshTokenRepository.findRefreshTokenByName(refreshToken).getName();

            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User user = userService.getByLogin(login)
                        .orElseThrow(() -> new JwtAuthenticationException("User not found", HttpStatus.FORBIDDEN));
                final String accessToken = jwtProvider.generateAccessToken(user);
                String clientIp = requestService.getClientIp(request);
                RefreshToken refreshTokenFromDb = iRefreshTokenRepository.findRefreshTokenByName(refreshToken);
                refreshTokenFromDb.setIpAddress(clientIp);
                iRefreshTokenRepository.save(refreshTokenFromDb);
                user.setLastTimeOnline(LocalDateTime.now());
                iUserRepository.save(user);

                return new JwtResponse(accessToken, null);
            }
        }

        return new JwtResponse(null, null);
    }

    public JwtResponse refresh(@NonNull String refreshToken, HttpServletRequest request) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final RefreshToken refreshTokenFromDb = iRefreshTokenRepository.findRefreshTokenByName(refreshToken);

            if (refreshTokenFromDb.getName() != null && refreshTokenFromDb.getName().equals(refreshToken)) {
                final User user = userService.getByLogin(login)
                        .orElseThrow(() -> new JwtAuthenticationException("User not found", HttpStatus.FORBIDDEN));
                final String newAccessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                String clientIp = requestService.getClientIp(request);
                refreshTokenFromDb.setName(newRefreshToken);
                refreshTokenFromDb.setIpAddress(clientIp);
                iRefreshTokenRepository.save(refreshTokenFromDb);

                return new JwtResponse(newAccessToken, newRefreshToken);
            }
        }

        throw new JwtAuthenticationException("JWT token is invalid", HttpStatus.FORBIDDEN);
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }
}
