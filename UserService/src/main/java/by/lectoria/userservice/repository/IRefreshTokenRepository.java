package by.lectoria.userservice.repository;

import by.lectoria.userservice.dao.RefreshToken;
import by.lectoria.userservice.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    List<RefreshToken> findRefreshTokenByUserId(User userId);
    void deleteByUserId(User userId);
}
