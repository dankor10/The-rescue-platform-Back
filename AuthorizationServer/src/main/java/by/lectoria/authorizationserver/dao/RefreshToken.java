package by.lectoria.authorizationserver.dao;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "refresh_token")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "user_agent")
    public String userAgent;

    @Column(name = "ip_address")
    public String ipAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userId;
}
