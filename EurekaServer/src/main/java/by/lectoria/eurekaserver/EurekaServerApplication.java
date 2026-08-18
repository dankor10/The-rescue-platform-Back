package by.lectoria.eurekaserver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
@Slf4j
public class EurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServerApplication.class, args);
		log.debug("Starting Sos Eureka Server application in debug with {} args", args.length);
		log.info("Starting Sos Eureka Server application with {} args.", args.length);
		System.out.println("The SOs Eureka Server is running");
	}

}
