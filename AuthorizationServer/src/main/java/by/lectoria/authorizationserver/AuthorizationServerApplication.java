package by.lectoria.authorizationserver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j(topic = "AuthorizationServerApplication")
@SpringBootApplication
public class AuthorizationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServerApplication.class, args);
        log.debug("Starting SosAuthorizationServer application in debug with {} args", args.length);
        log.info("Starting SosAuthorizationServer application with {} args.", args.length);
        System.out.println("The SosAuthorizationServer is running");
    }

}
