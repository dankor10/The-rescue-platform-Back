package by.lectoria.gatewayserver.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class ProxyConfig {
    @Bean
    RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("authorization_route",
                        route -> route.path("/auth/**")
                                .and().method(HttpMethod.POST)
                                .filters(filter -> filter.stripPrefix(1))
                                .uri("lb://Sos-Authorization-Server"))
                .route("user_management_route",
                        route -> route.path("/user-management/**")
                                .and().method(HttpMethod.GET, HttpMethod.DELETE, HttpMethod.POST, HttpMethod.PUT)
                                .filters(filter -> filter.stripPrefix(1))
                                .uri("lb://Sos-User-Service"))
                .build();
    }
}
