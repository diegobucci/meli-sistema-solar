package meli.tmr.solarsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SolarSystemApplication  {

	public static void main(String[] args) {
		SpringApplication.run(SolarSystemApplication.class, args);
	}

	@GetMapping("/")
	public String hello() {
		return "Welcome Solar System Service!";
	}
}
