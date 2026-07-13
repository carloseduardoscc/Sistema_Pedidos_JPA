package br.com.carloseduscc.resource_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ResourceServerApp {

	public static void main(String[] args) {
		SpringApplication.run(ResourceServerApp.class, args);
	}

}
