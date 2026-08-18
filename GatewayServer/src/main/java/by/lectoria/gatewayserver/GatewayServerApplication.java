package by.lectoria.gatewayserver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class GatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServerApplication.class, args);
		log.debug("Starting Sos Gateway Server application in debug with {} args", args.length);
		log.info("Starting Sos Gateway Server application with {} args.", args.length);
		System.out.println("The Sos Gateway Server is running");
	}

}
