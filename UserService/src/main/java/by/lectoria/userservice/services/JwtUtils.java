package by.lectoria.userservice.services;

import by.lectoria.userservice.model.JwtAuthentication;
import by.lectoria.userservice.model.Role;
import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtUtils {
    public static JwtAuthentication generate(Claims claims) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setRole(getRoles(claims));
        jwtInfoToken.setFirstName(claims.get("firstName", String.class));
        jwtInfoToken.setId(claims.get("id", Long.class));
        jwtInfoToken.setUsername(claims.getSubject());

        return jwtInfoToken;
    }

    private static Role getRoles(Claims claims) {
        final String roles = claims.get("roles", String.class);
        return Enum.valueOf(Role.class, roles);
    }
}
