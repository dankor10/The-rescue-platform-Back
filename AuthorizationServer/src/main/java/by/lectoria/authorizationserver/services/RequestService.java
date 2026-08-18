package by.lectoria.authorizationserver.services;

import jakarta.servlet.http.HttpServletRequest;

public interface RequestService {
    String getClientIp(HttpServletRequest request);
}
