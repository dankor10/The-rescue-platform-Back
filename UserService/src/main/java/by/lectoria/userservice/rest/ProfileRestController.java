package by.lectoria.userservice.rest;

import by.lectoria.userservice.dao.User;
import by.lectoria.userservice.model.Views;
import by.lectoria.userservice.repository.IUserRepository;
import by.lectoria.userservice.services.AuthService;
import by.lectoria.userservice.services.UserProfileService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users/profile")
@RequiredArgsConstructor
public class ProfileRestController {
    private final IUserRepository iUserRepository;
    private final AuthService authService;
    private final UserProfileService userProfileService;

    @PreAuthorize("hasAnyAuthority('users:read')")
    @JsonView(Views.UserView.Get.class)
    @GetMapping()
    public User getProfile() {
        return iUserRepository.findByLogin(authService.getAuthentication().getPrincipal().toString());
    }

    @Transactional
    @PreAuthorize("hasAnyAuthority('users:read')")
    @JsonView(Views.UserView.Get.class)
    @PutMapping()
    public ResponseEntity<?> getProfile(@RequestBody @JsonView(Views.UserView.Profile.class) User user) {
        return userProfileService.updateProfile(user);
    }
}
