package com.ariari.ariari;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AriariApplication {

	public static void main(String[] args) {
		SpringApplication.run(AriariApplication.class, args);
	}

}
