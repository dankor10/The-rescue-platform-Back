package by.lectoria.authorizationserver.repository;

import by.lectoria.authorizationserver.dao.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    RefreshToken findRefreshTokenByName(String name);
}