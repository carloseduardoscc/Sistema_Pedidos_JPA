package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SistemaPedidosJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaPedidosJpaApplication.class, args);
	}

}
