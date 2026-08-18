package by.lectoria.authorizationserver.services;

import by.lectoria.authorizationserver.dao.User;
import by.lectoria.authorizationserver.repository.IUserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final IUserRepository iUserRepository;

    public Optional<User> getByLogin(@NonNull String login) {
        User user = iUserRepository.findByLogin(login);

        if (user ==  null) {
            throw new UsernameNotFoundException("User not found");
        }

        return Optional.of(user);
    }

}
