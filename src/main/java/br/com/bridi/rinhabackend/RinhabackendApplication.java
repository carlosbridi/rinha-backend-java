package br.com.bridi.rinhabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class RinhabackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(RinhabackendApplication.class, args);
	}

}
