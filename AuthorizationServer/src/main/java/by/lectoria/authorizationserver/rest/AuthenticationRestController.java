package by.lectoria.authorizationserver.rest;

import by.lectoria.authorizationserver.dao.RefreshToken;
import by.lectoria.authorizationserver.repository.IRefreshTokenRepository;
import by.lectoria.authorizationserver.security.AuthService;
import by.lectoria.authorizationserver.security.JwtRequest;
import by.lectoria.authorizationserver.security.JwtResponse;
import by.lectoria.authorizationserver.security.RefreshJwtRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthenticationRestController {
    private final AuthService authService;
    private final IRefreshTokenRepository iRefreshTokenRepository;

    @PostMapping("login")
    public ResponseEntity<JwtResponse> login(@RequestHeader(value = "User-Agent") String userAgent,
                                             HttpServletRequest request,
                                             @RequestBody JwtRequest authRequest) {
        final JwtResponse token = authService.login(userAgent, request, authRequest);

        return ResponseEntity.ok(token);
    }

    @PostMapping("token")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody RefreshJwtRequest request,
                                                         HttpServletRequest httpServletRequest) {
        final JwtResponse token = authService.getAccessToken(request.getRefreshToken(), httpServletRequest);

        return ResponseEntity.ok(token);
    }

    @PostMapping("refresh")
    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody RefreshJwtRequest request,
                                                          HttpServletRequest httpServletRequest) {
        final JwtResponse token = authService.refresh(request.getRefreshToken(), httpServletRequest);

        return ResponseEntity.ok(token);
    }

    @PostMapping("/logout")
    public void logout(@RequestBody RefreshJwtRequest refreshToken, HttpServletRequest request, HttpServletResponse response) {
        RefreshToken refreshTokenFromDb = iRefreshTokenRepository.findRefreshTokenByName(refreshToken.getRefreshToken());
        iRefreshTokenRepository.deleteById(refreshTokenFromDb.getId());
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
