package by.lectoria.userservice.rest;

import by.lectoria.userservice.dao.User;
import by.lectoria.userservice.model.Views;
import by.lectoria.userservice.services.RegistrationService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/registration")
@RequiredArgsConstructor
public class UserRegistrationRestController {
    private final RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<?> registration(@RequestBody @JsonView(Views.UserView.Post.class) User user) {
        return registrationService.registrationUser(user);
    }
}
