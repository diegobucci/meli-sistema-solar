package meli.tmr.solarsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"meli.tmr.solarsystem"})
public class SolarSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SolarSystemApplication.class, args);
	}

}
