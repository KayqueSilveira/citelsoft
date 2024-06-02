package br.com.citelsoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CitelsoftApplication {
	public static void main(String[] args) {
		SpringApplication.run(CitelsoftApplication.class, args);
	}
}
