package by.lectoria.userservice.dao;

import by.lectoria.userservice.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "refresh_token")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.RefreshTokenView.Get.class)
    @Column(name = "id")
    private Long id;

    @JsonView(Views.RefreshTokenView.Put.class)
    @Column(name = "name")
    private String name;

    @JsonView(Views.RefreshTokenView.Get.class)
    @Column(name = "user_agent")
    public String userAgent;

    @JsonView(Views.RefreshTokenView.Get.class)
    @Column(name = "ip_address")
    public String ipAddress;

    @JsonView(Views.RefreshTokenView.Get.class)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userId;
}