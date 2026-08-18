package by.lectoria.userservice.dao;

import by.lectoria.userservice.model.Role;
import by.lectoria.userservice.model.Views;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.UserView.Get.class)
    @Column(name = "id")
    private Long id;

    @JsonView({Views.UserView.Get.class, Views.UserView.Post.class, Views.UserView.Put.class})
    @Column(name = "login", nullable = false)
    private String login;

    @JsonView({Views.UserView.Get.class, Views.UserView.Post.class, Views.UserView.Put.class})
    @Column(name = "password", nullable = false)
    private String password;

    @JsonView({Views.UserView.Get.class, Views.UserView.Post.class, Views.UserView.Put.class})
    @Column(name = "email")
    private String email;

    @JsonView({Views.UserView.Get.class, Views.UserView.Post.class, Views.UserView.Put.class})
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @JsonView({Views.UserView.Get.class, Views.UserView.Post.class, Views.UserView.Put.class})
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @JsonView(Views.UserView.Get.class)
    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @JsonView(Views.UserView.Get.class)
    @Column(name = "date_modified")
    private LocalDateTime dateModified;

    @JsonView(Views.UserView.Get.class)
    @Column(name = "last_time_online")
    private LocalDateTime lastTimeOnline;

    @JsonView({Views.UserView.Get.class, Views.UserView.Post.class, Views.UserView.Put.class})
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonView({Views.UserView.Get.class, Views.UserView.Post.class, Views.UserView.Put.class})
    @Column(name = "blocked", nullable = false)
    private Boolean blocked;

    @JsonView({Views.UserView.Get.class, Views.UserView.Put.class})
    @Column(name = "protection_from_deletion", nullable = false)
    private Boolean protectionFromDeletion;

    @JsonView(Views.UserView.Get.class)
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RefreshToken> refreshTokens;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
