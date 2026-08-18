package by.lectoria.authorizationserver.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshJwtRequest {
    public String refreshToken;
}
