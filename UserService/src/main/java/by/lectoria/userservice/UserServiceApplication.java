package by.lectoria.userservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
        log.debug("Starting SOSUserService application in debug with {} args", args.length);
        log.info("Starting SOSUserService application with {} args.", args.length);
        System.out.println("The SosUserService is running");
    }

}
