package com.api.coindesk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class CoindeskApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoindeskApplication.class, args);
	}

}
